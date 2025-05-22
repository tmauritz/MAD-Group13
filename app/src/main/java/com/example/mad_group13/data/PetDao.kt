package com.example.mad_group13.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PetDao {

    //gets the most recent pet from the database
    @Query("SELECT * FROM pet ORDER BY id DESC")
    suspend fun getAllPetsByIdDescending(): List<Pet>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPet(pet: Pet)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePet(pet: Pet)

}