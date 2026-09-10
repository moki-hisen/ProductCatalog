package com.mk.productcatalog.ui.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.productcatalog.data.remote.RetrofitInstance
import com.mk.productcatalog.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel : ViewModel() {

    private val repository = ProductRepository(RetrofitInstance.api)

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    private var currentProductId: Int? = null

    fun loadProduct(productId: Int) {
        if (currentProductId == productId && _uiState.value.product != null) {
            return
        }

        currentProductId = productId

        _uiState.value = ProductDetailUiState(
            isLoading = true
        )

        viewModelScope.launch {
            repository.getProduct(productId)
                .onSuccess { product ->
                    _uiState.value = ProductDetailUiState(
                        product = product
                    )
                }
                .onFailure { exception ->
                    _uiState.value = ProductDetailUiState(
                        error = exception.message ?: "Failed to load product"
                    )
                }
        }
    }

    fun retry() {
        currentProductId?.let { productId ->
            loadProduct(productId)
        }
    }
}