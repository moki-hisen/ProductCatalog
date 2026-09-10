package com.mk.productcatalog.ui.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.productcatalog.data.model.Product
import com.mk.productcatalog.data.remote.RetrofitInstance
import com.mk.productcatalog.data.repository.ProductRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductListViewModel : ViewModel() {

    private val repository = ProductRepository(RetrofitInstance.api)

    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    private val pageSize = 20
    private var currentSkip = 0

    private var searchJob: Job? = null

    init {
        loadProducts()
    }

    fun loadProducts() {
        if (_uiState.value.isLoading) return

        currentSkip = 0

        _uiState.value = _uiState.value.copy(
            isLoading = true,
            isLoadingMore = false,
            error = null,
            products = emptyList(),
            hasMore = true
        )

        viewModelScope.launch {
            repository.getProducts(
                limit = pageSize,
                skip = currentSkip
            ).onSuccess { products ->
                _uiState.value = _uiState.value.copy(
                    products = products,
                    isLoading = false,
                    error = null,
                    hasMore = products.size == pageSize
                )

                currentSkip += products.size
            }.onFailure { exception ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = exception.message ?: "Failed to load products"
                )
            }
        }
    }

    fun loadMoreProducts() {
        val state = _uiState.value

        if (state.isLoading || state.isLoadingMore || !state.hasMore) {
            return
        }

        _uiState.value = state.copy(
            isLoadingMore = true,
            error = null
        )

        viewModelScope.launch {
            repository.getProducts(
                limit = pageSize,
                skip = currentSkip
            ).onSuccess { products ->
                _uiState.value = _uiState.value.copy(
                    products = _uiState.value.products + products,
                    isLoadingMore = false,
                    hasMore = products.size == pageSize
                )

                currentSkip += products.size
            }.onFailure { exception ->
                _uiState.value = _uiState.value.copy(
                    isLoadingMore = false,
                    error = exception.message ?: "Failed to load more products"
                )
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.value = _uiState.value.copy(
            searchQuery = query
        )

        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(400)

            if (query.isBlank()) {
                loadProducts()
            } else {
                searchProducts(query.trim())
            }
        }
    }

    private fun searchProducts(query: String) {
        currentSkip = 0

        _uiState.value = _uiState.value.copy(
            isLoading = true,
            isLoadingMore = false,
            error = null,
            products = emptyList(),
            hasMore = false
        )

        viewModelScope.launch {
            repository.searchProducts(
                query = query,
                limit = pageSize,
                skip = 0
            ).onSuccess { products ->
                _uiState.value = _uiState.value.copy(
                    products = products,
                    isLoading = false,
                    error = null,
                    hasMore = false
                )
            }.onFailure { exception ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = exception.message ?: "Failed to search products"
                )
            }
        }
    }

    fun retry() {
        val query = _uiState.value.searchQuery

        if (query.isBlank()) {
            loadProducts()
        } else {
            searchProducts(query.trim())
        }
    }
}