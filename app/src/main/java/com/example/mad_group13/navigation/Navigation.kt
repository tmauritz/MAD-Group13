package com.example.mad_group13.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.mad_group13.MainScreen
import com.example.mad_group13.presentation.CurrentPetStats

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(
                onShowCurrentPetStats = { navController.navigate(Screen.ActivePetStats.route)},
                modifier = modifier
            )
        }
        composable(route = Screen.ActivePetStats.route){
            CurrentPetStats(
                onBackToMain = {navController.navigate(Screen.MainScreen.route)},
                modifier = modifier)
        }
    }
}
