@file:Suppress("PropertyName")

package de.dias.dominik.model.http

import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val count: Int,
    val page: Int,
    val page_count: Int,
    val page_size: Int,
    val products: List<Product>,
    val skip: Int,
)

@Serializable
data class Product(
    val product_name: String,
    val image_thumb_url: String? = null,
)
