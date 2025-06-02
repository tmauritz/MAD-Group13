package com.example.mad_group13.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TeslaStock(
    @PrimaryKey val id: Int = 1, // only one row, overwrite always
    val lastPrice: Float = 0f,
    val lastUpdated: Long = System.currentTimeMillis()
)
