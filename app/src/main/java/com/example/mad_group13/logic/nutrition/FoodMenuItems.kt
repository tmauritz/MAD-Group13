package com.example.mad_group13.logic.nutrition

data class FoodSnackMenuItems(
    val foodList: List<Food> = listOf(
        BasicFood(
            name = TODO(),
            nutritionValue = TODO()
        ),
        BasicSnack(
            name = "Sugar Cube",
            nutritionValue = .04f,
            applyEffect = {pet -> pet.copy(happiness = pet.happiness*1.02f) } //make pet 2% happier
        )
    )
)
