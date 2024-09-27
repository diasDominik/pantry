package de.dias.dominik

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import de.dias.dominik.model.database.repository.PantryRepository
import de.dias.dominik.model.http.Client
import de.dias.dominik.ui.theme.PantryTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    private val options =
        GmsBarcodeScannerOptions
            .Builder()
            .setBarcodeFormats(Barcode.FORMAT_EAN_8, Barcode.FORMAT_EAN_13)
            .enableAutoZoom()
            .build()

    private val mainActivityViewModel: MainActivityViewModel by inject()
    private val pantryRepository: PantryRepository by inject()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val scanner = GmsBarcodeScanning.getClient(this, options)

        var url by mutableStateOf("")
        setContent {
            val scope = rememberCoroutineScope()
            KoinContext {
                PantryTheme {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            TopAppBar(title = { Text("Pantry") })
                        },
                    ) { innerPadding ->
                        Column(
                            modifier = Modifier.padding(innerPadding),
                            horizontalAlignment = CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Row {
                                TextField(
                                    value = mainActivityViewModel.barcodeText,
                                    onValueChange = mainActivityViewModel::onTextChange,
                                    trailingIcon = {
                                        IconButton(
                                            onClick = {
                                                scope.launch {
                                                    mainActivityViewModel
                                                        .requestProduct(
                                                            mainActivityViewModel.barcodeText.toLong(),
                                                        ).collect {
                                                            url = it.products.firstOrNull()?.image_thumb_url ?: ""
                                                        }
                                                }
                                            },
                                        ) {
                                            Icon(
                                                painter = rememberVectorPainter(Icons.Filled.Search),
                                                contentDescription = null,
                                            )
                                        }
                                    },
                                    keyboardOptions =
                                        KeyboardOptions(
                                            capitalization = KeyboardCapitalization.Characters,
                                            keyboardType = KeyboardType.Number,
                                        ),
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                IconButton(
                                    onClick = {
                                        scanner
                                            .startScan()
                                            .addOnSuccessListener { barcode ->
                                                barcode.rawValue?.let {
                                                    scope.launch {
                                                        mainActivityViewModel
                                                            .requestProduct(
                                                                mainActivityViewModel.barcodeText.toLong(),
                                                            ).collect {
                                                                url =
                                                                    it.products.firstOrNull()?.image_thumb_url
                                                                        ?: ""
                                                            }
                                                    }
                                                }
                                            }
                                    },
                                ) {
                                    Icon(
                                        painter = rememberVectorPainter(Icons.Filled.Camera),
                                        contentDescription = null,
                                    )
                                }
                            }
                            LaunchedEffect(Unit) {
                                pantryRepository.getAll().collect { item ->
                                    item.forEach {
                                        println(it.name)
                                    }
                                }
                            }
                            if (url.isNotEmpty()) {
                                AsyncImage(
                                    model = url,
                                    contentDescription = null,
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Client.close()
    }
}
