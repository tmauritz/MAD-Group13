package com.example.mad_group13.logic.nutrition

import com.example.mad_group13.data.Pet

interface Snack: Food {
    val applyEffect: (Pet)->Pet
}