package com.example.mad_group13.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: TeslaApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.alphavantage.co/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TeslaApiService::class.java)
    }
}