package com.example.mad_group13.data

import android.content.Context
import android.util.Log
import com.example.mad_group13.model.RetrofitInstance
import javax.inject.Inject

class TeslaStockRepository @Inject constructor(
    private val teslaStockDao: TeslaStockDao,
    private val petRepository: PetRepository
) {
    suspend fun getLastTeslaPrice(): Float? {
        return teslaStockDao.getTeslaStock()?.lastPrice
    }

    suspend fun saveTeslaPrice(price: Float) {
        val stock = TeslaStock(id = 1, lastPrice = price, lastUpdated = System.currentTimeMillis())
        teslaStockDao.insertOrUpdateTeslaStock(stock)
    }
    private var lastTeslaPrice: Float? = null

    suspend fun fetchAndSaveTeslaPriceOnly() {
        try {
            val response = RetrofitInstance.api.getTeslaQuote(apiKey = "9ODY479B9EZMO0V6")
            val price = response.globalQuote.price.toFloatOrNull()
            if (price != null) {
                teslaStockDao.insertOrUpdateTeslaStock(
                    TeslaStock(id = 1, lastPrice = price, lastUpdated = System.currentTimeMillis())
                )
                lastTeslaPrice = price
                Log.i("TeslaStockRepo", "Saved Tesla price: $price")
            }
        } catch (e: Exception) {
            Log.e("TeslaStockRepo", "Error fetching Tesla price", e)
        }
    }
}
