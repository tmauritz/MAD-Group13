package com.example.mad_group13.logic.nutrition

sealed class FoodSnackMenuItems(val foodList: List<Food>){
    object itemList: FoodSnackMenuItems(listOf(
        BasicFood(
            name = "Food Item 1",
            nutritionValue = .1f
            ),
        BasicSnack(
            name = "Sugar Cube",
            nutritionValue = .04f,
            applyEffect = {pet -> pet.copy(happiness = pet.happiness*1.02f) } //make pet 2% happier
            ),
        BasicFood(
            name = "Carrot",
            nutritionValue = .08f
            ),
        BasicFood(
            name = "Apple",
            nutritionValue = .12f
            ),
        BasicSnack(
            name = "Chocolate Treat",
            nutritionValue = .05f,
            applyEffect = { pet -> pet.copy(happiness = pet.happiness * 1.05f) } // +5% happiness
            ),
        BasicFood(
            name = "Grain Mix",
            nutritionValue = .15f
            ),
        BasicSnack(
            name = "Energy Bar",
            nutritionValue = .07f,
            applyEffect = { pet -> pet.copy(activity = pet.activity * 1.03f) } // +3% activity
            ),
        BasicFood(
            name = "Vegetable Stew",
            nutritionValue = .2f
            ),
        BasicSnack(
            name = "Vitamin Drop",
            nutritionValue = .03f,
            applyEffect = { pet -> pet.copy(health = pet.health * 1.04f) } // +4% health
            ),
        BasicSnack(
            name = "Frozen Berry",
            nutritionValue = .06f,
            applyEffect = { pet -> pet.copy(happiness = pet.happiness * 1.01f, health = pet.health * 1.01f) } // +1% happiness & health
            )
        )
    )
}