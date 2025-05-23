package com.example.mad_group13.logic.nutrition

import com.example.mad_group13.data.Pet

class BasicFood(override val name: String, override val nutritionValue: Float) :Food{
    override fun feed(pet: Pet): Pet {
        return pet.copy(hunger = pet.hunger+nutritionValue)
    }
}