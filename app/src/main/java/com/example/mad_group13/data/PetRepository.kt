package com.example.mad_group13.data

import android.util.Log
import javax.inject.Inject

class PetRepository @Inject constructor(
    private val petDao: PetDao
){
    suspend fun getActivePet(): Pet{
        Log.i("MAD_dao","Pulling Pets")
        val activePets: List<Pet> = petDao.getActivePets()
        Log.i("MAD_dao", "ActivePetSize: ${activePets.size}")
        return if (activePets.isEmpty()){Pet()}
        else activePets.first()
    }

    suspend fun updatePet(pet: Pet) {
        val activePets = petDao.getActivePets()
        val existing = activePets.find { it.id == pet.id }
        if (existing != null) {
            petDao.updatePet(pet)
        } else {
            petDao.insertPet(pet)
        }
    }

}