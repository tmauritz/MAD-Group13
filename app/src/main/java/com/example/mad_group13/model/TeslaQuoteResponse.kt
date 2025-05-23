package com.example.mad_group13.model

import com.google.gson.annotations.SerializedName




data class TeslaQuoteResponse(
    @SerializedName("Global Quote") val globalQuote: Quote
)

data class Quote(
    @SerializedName("05. price") val price: String
)
