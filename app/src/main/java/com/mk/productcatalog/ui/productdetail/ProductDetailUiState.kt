package com.mk.productcatalog.ui.productdetail

import com.mk.productcatalog.data.model.Product

data class ProductDetailUiState(
    val product: Product? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)