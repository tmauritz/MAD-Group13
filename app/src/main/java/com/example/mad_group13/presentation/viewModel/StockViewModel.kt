package com.example.mad_group13.presentation.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mad_group13.data.TeslaStockRepository
import com.example.mad_group13.model.TeslaStockFetcher
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class StockViewModel @Inject constructor(
    private val teslaStockRepository: TeslaStockRepository,
    @ApplicationContext private val appContext: Context
) : ViewModel() {

    private val _teslaPrice = MutableStateFlow("Load...")
    val teslaPrice: StateFlow<String> = _teslaPrice

    var onTeslaPriceChanged: ((Float) -> Unit)? = null

    fun fetchTeslaStock() {
        viewModelScope.launch {
            val priceStr = TeslaStockFetcher.fetchTeslaPrice()
            val price = priceStr?.toFloatOrNull()

            if (price != null) {
                val lastTeslaPrice = teslaStockRepository.getLastTeslaPrice()
                if (lastTeslaPrice != null && lastTeslaPrice > 0f) {
                    val percentageChange = ((price - lastTeslaPrice) / lastTeslaPrice) * 100f
                    onTeslaPriceChanged?.invoke(percentageChange)
                    Log.i("StockViewModel", "Yesterday: $lastTeslaPrice, Today: $price, Change: $percentageChange")
                }

                teslaStockRepository.saveTeslaPrice(price)
                _teslaPrice.value = priceStr
            } else {
                _teslaPrice.value = "Error"
            }
        }
    }

    fun setFakeTeslaPrice(fakePrice: Float) {
        viewModelScope.launch {
            teslaStockRepository.saveTeslaPrice(fakePrice)
            _teslaPrice.value = "$fakePrice $"
            Log.i("Debug", "Fake Tesla price set to $fakePrice")
        }
    }

    fun applyFakePrice(price: Float) {
        viewModelScope.launch {
            val lastTeslaPrice = teslaStockRepository.getLastTeslaPrice()
            if (lastTeslaPrice != null && lastTeslaPrice > 0f) {
                val percentageChange = ((price - lastTeslaPrice) / lastTeslaPrice) * 100f
                onTeslaPriceChanged?.invoke(percentageChange)
                Log.i("StockViewModel", "Fake price change: $percentageChange%")
            }
            teslaStockRepository.saveTeslaPrice(price)
            _teslaPrice.value = price.toString()
        }
    }

}