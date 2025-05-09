package com.example.mad_group13.navigation

sealed class Screen (val route: String) {
    object MainScreen: Screen("home")
    object ActivePetStats: Screen("active_pet_stats")
}