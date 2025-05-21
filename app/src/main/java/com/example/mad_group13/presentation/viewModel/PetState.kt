package com.example.mad_group13.model

data class PetState (
    val maturity: PetMaturity = PetMaturity.BABY,
    val age: Float = 0f,
    val health: Float = 1f,
    val happiness: Float = 0f,
    val hunger: Float = 0.2f,
    val activity: Float = 0f
)

enum class PetMaturity {    // feel free to refactor into separate file,
    BABY {},                // for now it makes sense to just have it here tho
    TEEN {},
    ADULT {}
}