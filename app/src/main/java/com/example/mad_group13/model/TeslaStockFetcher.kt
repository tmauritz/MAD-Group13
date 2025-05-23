package com.example.mad_group13.model


import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object TeslaStockFetcher {

    //old function, maybe using it later
    fun fetchAndNotifyIfNeeded(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.getTeslaQuote(apiKey = "9ODY479B9EZMO0V6")
                val price = response.globalQuote.price.toFloat()

                if (price < 700f) {
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(context, "TSLA has fallen! price: $price", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Method for ViewModel Access
    suspend fun fetchTeslaPrice(): String? {
        return try {
            val response = RetrofitInstance.api.getTeslaQuote(apiKey = "9ODY479B9EZMO0V6")
            val price = response.globalQuote.price
            "$price $"
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}