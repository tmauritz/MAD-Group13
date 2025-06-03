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

    suspend fun fetchAndApplyHappinessChange(context: Context) {
        try {
            //val response = RetrofitInstance.api.getTeslaQuote(apiKey = "9ODY479B9EZMO0V6")
            //val price = response.globalQuote.price.toFloatOrNull()
            val price = 460f
            if (price != null) {
                val yesterdayPrice = teslaStockDao.getTeslaStock()?.lastPrice
                if (yesterdayPrice != null && yesterdayPrice > 0f) {
                    val percentageChange = ((price - yesterdayPrice) / yesterdayPrice) * 100f

                    Log.i("TeslaStockRepo", "Price change: $percentageChange%")
                    Log.i("TeslaRepo", "Start fetching Tesla price")
                    Log.i("TeslaRepo", "Fetched price: $price, Last price: $yesterdayPrice")


                    val pet = petRepository.getActivePet()
                    val updatedPet = if (percentageChange > 0) {
                        pet.copy(happiness = (pet.happiness - percentageChange / 100f).coerceIn(0f, 1f))
                    } else {
                        pet.copy(happiness = (pet.happiness + (-percentageChange) / 100f).coerceIn(0f, 1f))
                    }
                    teslaStockDao.insertOrUpdateTeslaStock(TeslaStock(id = 1, lastPrice = price, lastUpdated = System.currentTimeMillis()))
                    petRepository.updatePet(updatedPet)
                    Log.i("TeslaStockRepo", "Updated happiness from ${pet.happiness} to ${updatedPet.happiness}")
                } else {
                    Log.w("TeslaRepo", "No previous price stored → skipping happiness update")
                }
                lastTeslaPrice = price
            }
        } catch (e: Exception) {
            Log.e("TeslaStockRepo", "Error fetching Tesla price", e)
        }
    }
}
