package com.example.mad_group13.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TeslaStockDao {

    @Query("SELECT * FROM TeslaStock WHERE id = 1")
    suspend fun getTeslaStock(): TeslaStock?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateTeslaStock(stock: TeslaStock)
}
