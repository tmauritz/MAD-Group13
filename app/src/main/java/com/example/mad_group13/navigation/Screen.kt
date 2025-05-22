package com.example.mad_group13.navigation

sealed class Screen (val route: String) {
    object StartScreen: Screen("home")
    object MainScreen: Screen("main")
    object ActivePetStats: Screen("active_pet_stats")
    object PetHistory: Screen("pet_history")
}