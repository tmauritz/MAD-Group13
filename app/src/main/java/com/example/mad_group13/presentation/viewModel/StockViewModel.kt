package com.example.mad_group13.presentation.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mad_group13.model.TeslaStockFetcher
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class StockViewModel @Inject constructor() : ViewModel() {

    private val _teslaPrice = MutableStateFlow("Load...")
    val teslaPrice: StateFlow<String> = _teslaPrice

    init {
        fetchTeslaStock()
    }

    fun fetchTeslaStock() {
        viewModelScope.launch {
            val price = TeslaStockFetcher.fetchTeslaPrice()
            _teslaPrice.value = price ?: "Error"
        }
    }
}