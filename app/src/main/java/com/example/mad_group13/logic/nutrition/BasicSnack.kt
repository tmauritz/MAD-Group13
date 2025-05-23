package com.example.mad_group13.logic.nutrition

import com.example.mad_group13.data.Pet

class BasicSnack(override val name: String, override val nutritionValue: Float,
                 override val applyEffect: (Pet) -> Pet
): Snack{

    override fun feed(pet: Pet): Pet {
        return pet.copy(hunger = pet.hunger+nutritionValue)
    }

}
