package com.example.mad_group13.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mad_group13.StartScreen


import com.example.mad_group13.presentation.CurrentStatScreen
import com.example.mad_group13.presentation.FoodMenuScreen
import com.example.mad_group13.presentation.MainScreen
import com.example.mad_group13.presentation.PetHistory

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val backToMainMenu: () -> Unit = { navController.popBackStack(Screen.MainScreen.route, inclusive = false, saveState = false) }

    NavHost(
        navController = navController,
        startDestination = Screen.StartScreen.route
    ) {
        composable(route = Screen.StartScreen.route) {
            StartScreen(
                onShowMainScreen = { navController.navigate(Screen.MainScreen.route) {
                    popUpTo(Screen.StartScreen.route) { inclusive = true }
                } },
                modifier = modifier
            )
        }
        composable(route = Screen.MainScreen.route) {
            MainScreen(
                onNavigatePetHistory = {navController.navigate(Screen.PetHistory.route)},
                modifier = modifier
            )
        }
        composable(route = Screen.ActivePetStats.route){
            CurrentStatScreen(
                onBackToMain = backToMainMenu,
                modifier = modifier)
        }
        composable(route = Screen.PetHistory.route) {
            PetHistory(
                onBackNavigation = backToMainMenu,
                modifier = modifier,
            )
        }
        composable(route = Screen.FoodMenu.route) {
            FoodMenuScreen(
                onBack = backToMainMenu,
                modifier = modifier
            )
        }
    }
}
