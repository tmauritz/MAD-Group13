package com.example.mad_group13.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mad_group13.model.TeslaStockFetcher
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class StockViewModel @Inject constructor() : ViewModel() {

    private val _teslaPrice = MutableStateFlow("Load...")
    val teslaPrice: StateFlow<String> = _teslaPrice

    private var lastTeslaPrice: Float? = null

    var onTeslaPriceChanged: ((Float) -> Unit)? = null

    init {
        fetchTeslaStock()
    }

    fun fetchTeslaStock() {
        viewModelScope.launch {
            val priceStr = TeslaStockFetcher.fetchTeslaPrice()
            val price = priceStr?.toFloatOrNull()

            if (price != null && lastTeslaPrice != null) {
                val yesterday = lastTeslaPrice!!
                val today = price

                if (yesterday > 0f) {
                    val percentageChange = ((today - yesterday) / yesterday) * 100f

                    onTeslaPriceChanged?.invoke(percentageChange)
                }
            }

            if (price != null) {
                lastTeslaPrice = price
            }

            _teslaPrice.value = priceStr ?: "Error"
        }
    }
}