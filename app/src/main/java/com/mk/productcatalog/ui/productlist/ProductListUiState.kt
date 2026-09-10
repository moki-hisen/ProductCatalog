package com.mk.productcatalog.ui.productlist

import com.mk.productcatalog.data.model.Product

data class ProductListUiState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val hasMore: Boolean = true
)