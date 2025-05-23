package com.example.mad_group13.logic.nutrition

import com.example.mad_group13.data.Pet

interface Food {
    val name: String
    val nutritionValue: Float
    fun feed(pet: Pet): Pet
}