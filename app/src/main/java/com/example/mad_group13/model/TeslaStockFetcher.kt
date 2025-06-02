package com.example.mad_group13.model

import android.util.Log

object TeslaStockFetcher {

    // Method for ViewModel Access
    suspend fun fetchTeslaPrice(): String? {
        return try {
            val response = RetrofitInstance.api.getTeslaQuote(apiKey = "9ODY479B9EZMO0V6")
            Log.d("TeslaStockFetcher", "Response: $response")
            val price = response.globalQuote?.price
            if (price != null) price else {
                Log.e("TeslaStockFetcher", "Price was null!")
                null
            }
        } catch (e: Exception) {
            Log.e("TeslaStockFetcher", "Error fetching Tesla price", e)
            null
        }
    }
}