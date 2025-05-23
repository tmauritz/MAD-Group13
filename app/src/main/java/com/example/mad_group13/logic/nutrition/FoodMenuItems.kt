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
            )
        )
        //TODO: more food/snacks...
    )
}
