package com.example.mad_group13.model

object TeslaStockFetcher {

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