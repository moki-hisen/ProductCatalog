package com.mk.productcatalog.data.repository

import com.mk.productcatalog.data.model.Product
import com.mk.productcatalog.data.remote.ProductApi

class ProductRepository(
    private val api: ProductApi
) {

    suspend fun getProducts(
        limit: Int,
        skip: Int
    ): Result<List<Product>> {
        return try {
            val response = api.getProducts(limit, skip)
            Result.success(response.products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchProducts(
        query: String,
        limit: Int,
        skip: Int
    ): Result<List<Product>> {
        return try {
            val response = api.searchProducts(query, limit, skip)
            Result.success(response.products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getProduct(id: Int): Result<Product> {
        return try {
            Result.success(api.getProduct(id))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}