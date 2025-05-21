package com.example.mad_group13.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mad_group13.StartScreen


import com.example.mad_group13.presentation.CurrentStatScreen
import com.example.mad_group13.presentation.MainScreen

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val backToMainMenu: () -> Unit = { navController.popBackStack(Screen.MainScreen.route, inclusive = false, saveState = false) }

    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route // !!change to StartScreen later!!
    ) {
        composable(route = Screen.StartScreen.route) {
            StartScreen(
                onShowMainScreen = { navController.navigate(Screen.MainScreen.route)},
                modifier = modifier
            )
        }
        composable(route = Screen.MainScreen.route) {
            MainScreen(
                onShowCurrentPetStats = { navController.navigate(Screen.ActivePetStats.route)},
                modifier = modifier
            )
        }
        composable(route = Screen.ActivePetStats.route){
            CurrentStatScreen(
                onBackToMain = backToMainMenu,
                modifier = modifier)
        }
    }
}
