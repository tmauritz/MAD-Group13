package com.example.mad_group13.data

import android.content.Context
import android.util.Log
import com.example.mad_group13.util.WidgetUpdater
import javax.inject.Inject
import kotlin.random.Random


class PetRepository @Inject constructor(
    private val petDao: PetDao,
    private val context: Context

){
    suspend fun getActivePet(): Pet{
        Log.i("MAD_dao","Pulling Pets")
        val activePets: List<Pet> = petDao.getAllPetsByIdDescending()
        Log.i("MAD_dao", "ActivePetSize: ${activePets.size}")
        return if (activePets.isEmpty()){getNewPet()}
        else activePets.first()
    }

    suspend fun getPetHistory(): List<Pet>{
        return petDao.getAllPetsByIdDescending().drop(1)
    }

    suspend fun updatePet(pet: Pet) {
        val activePets = petDao.getAllPetsByIdDescending()
        val existing = activePets.find { it.id == pet.id }
        if (existing != null) {
            petDao.updatePet(pet)
        } else {
            petDao.insertPet(pet)
        }

        // Save values to SharedPreferences
        val prefs = context.getSharedPreferences("pet_prefs", Context.MODE_PRIVATE)
        prefs.edit()
            .putInt("health", (pet.health * 100).toInt())
            .putInt("hunger", (pet.hunger * 100).toInt())
            .putInt("happiness", (pet.happiness * 100).toInt())
            .putInt("activity", (pet.activity * 100).toInt())
            .apply()

        // After Update → Widget updating
        WidgetUpdater.updatePetStatsWidget(context)
    }

    suspend fun getNewPet(): Pet{
        val newPet = Pet(
            nickname = getPetName(),
            hunger = .5f + (Random.nextFloat()/2),
            activity = .5f + (Random.nextFloat()/2)
        )
        // insert pet, then retrieve it again to get the auto-incremented index from the DB
        petDao.insertPet(newPet)
        return petDao.getAllPetsByIdDescending().first()
    }

}