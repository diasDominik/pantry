package de.dias.dominik

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.dias.dominik.model.database.repository.PantryRepository
import de.dias.dominik.model.http.Client
import de.dias.dominik.model.http.ProductResponse
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val pantryRepository: PantryRepository,
) : ViewModel() {
    var barcodeText by mutableStateOf("")
        private set

    fun requestProduct(ean: String): Flow<ProductResponse> =
        callbackFlow {
            viewModelScope.launch {
                try {
                    val productResponse = Client.requestProduct(ean)
                    trySend(productResponse)
                } finally {
                    close()
                }
            }
            awaitClose {
                this.cancel()
            }
        }

    fun onTextChange(text: String) {
        this.barcodeText = text
    }
}
