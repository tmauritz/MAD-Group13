package com.example.mad_group13.data

import android.util.Log
import javax.inject.Inject
import kotlin.random.Random

class PetRepository @Inject constructor(
    private val petDao: PetDao
){
    suspend fun getActivePet(): Pet{
        Log.i("MAD_dao","Pulling Pets")
        val activePets: List<Pet> = petDao.getActivePets()
        Log.i("MAD_dao", "ActivePetSize: ${activePets.size}")
        return if (activePets.isEmpty()){getNewPet()}
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

    suspend fun getNewPet(): Pet{
        val newPet = Pet(nickname = getPetName(),
            hunger = .5f + (Random.nextFloat()/2),
            activity = .5f + (Random.nextFloat()/2)
        )
        // insert pet, then retrieve it again to get the auto-incremented index from the DB
        petDao.insertPet(newPet)
        return petDao.getActivePets().first()
    }

}