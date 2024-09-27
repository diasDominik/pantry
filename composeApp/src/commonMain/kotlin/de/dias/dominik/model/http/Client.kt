package de.dias.dominik.model.http

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object Client {
    private var clientInitialized = false

    private val client =
        HttpClient(CIO) {
            install(UserAgent) {
                agent = "Pantry/0.0.1 (https://github.com/diasdominik/pantry)"
            }
            install(ContentNegotiation) {
                json()
            }
        }

    suspend fun requestProduct(ean: Long) =
        withContext(Dispatchers.IO) {
            clientInitialized = true

            val response: ProductResponse =
                client
                    .get(
                        "https://world.openfoodfacts.org/api/v2/search?code=$ean&fields=product_name,image_thumb_url",
                    ).body()
            return@withContext response
        }

    fun close() {
        if (clientInitialized) {
            client.close()
        }
    }
}
