package com.mk.productcatalog.data.model

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val rating: Double,
    val thumbnail: String,
    val images: List<String>
)