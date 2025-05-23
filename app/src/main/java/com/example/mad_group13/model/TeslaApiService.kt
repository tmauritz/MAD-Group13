package com.example.mad_group13.model

import retrofit2.http.GET
import retrofit2.http.Query


interface TeslaApiService {
    @GET("query")
    suspend fun getTeslaQuote(
        @Query("function") function: String = "GLOBAL_QUOTE",
        @Query("symbol") symbol: String = "TSLA",
        @Query("apikey") apiKey: String
    ): TeslaQuoteResponse
}