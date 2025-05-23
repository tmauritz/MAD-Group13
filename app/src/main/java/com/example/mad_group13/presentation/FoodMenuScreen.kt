package com.example.mad_group13.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mad_group13.logic.nutrition.FoodSnackMenuItems
import com.example.mad_group13.logic.nutrition.Snack
import com.example.mad_group13.presentation.viewModel.PetStateViewModel

@Composable
fun FoodMenuScreen(
    onBack: () -> Unit,
    petStateViewModel: PetStateViewModel = hiltViewModel()
) {
    val foodItems = FoodSnackMenuItems.itemList.foodList

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("🍽️ Speisekarte", modifier = Modifier.padding(bottom = 8.dp))

        for (food in foodItems) {
            Button(onClick = {
                when (food) {
                    is Snack -> petStateViewModel.feedPet(food)
                    else -> petStateViewModel.feedPet(food)
                }
            }) {
                Text("${food.name} (+${(food.nutritionValue * 100).toInt()} Hunger)")
            }
        }

        Button(onClick = onBack, modifier = Modifier.padding(top = 24.dp)) {
            Text("Zurück")
        }
    }
}