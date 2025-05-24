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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.mad_group13.R
import com.example.mad_group13.logic.nutrition.FoodSnackMenuItems
import com.example.mad_group13.logic.nutrition.Snack
import com.example.mad_group13.presentation.viewModel.PetStateViewModel

@Composable
fun FoodMenuScreen(
    onBack: () -> Unit,
    petStateViewModel: PetStateViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val foodItems = FoodSnackMenuItems.itemList.foodList
    val activePet by petStateViewModel.petState.collectAsState()

    DisposableEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(petStateViewModel)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(petStateViewModel)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Menu", modifier = Modifier.padding(bottom = 8.dp))

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
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier.fillMaxWidth()){
            StatDisplay(stringResource(R.string.label_stat_health), activePet.health)
            StatDisplay(stringResource(R.string.label_stat_hunger), activePet.hunger)
            StatDisplay(stringResource(R.string.label_stat_happiness), activePet.happiness)
            StatDisplay(stringResource(R.string.label_stat_activity), activePet.activity)
        }

        Button(onClick = onBack, modifier = Modifier.padding(top = 24.dp)) {
            Text("Back")
        }
    }
}

@Composable
@Preview(name = "FoodMenuScreenPreview")
fun FoodMenuScreenPreview() {
    FoodMenuScreen(
        onBack = {},
    )
}