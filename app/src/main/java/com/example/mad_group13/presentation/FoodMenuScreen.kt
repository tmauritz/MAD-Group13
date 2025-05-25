package com.example.mad_group13.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
    modifier: Modifier = Modifier,
    petStateViewModel: PetStateViewModel = hiltViewModel(),
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

    Card(){
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(R.string.label_menu), modifier = Modifier.padding(bottom = 8.dp))
            LazyColumn { items(foodItems){food -> Button(onClick = {
                when (food) {
                    is Snack -> petStateViewModel.feedPet(food)
                    else -> petStateViewModel.feedPet(food)
                }
            }) {
                Text(
                    "${food.name} (${
                        stringResource(
                            R.string.label_food_hunger,
                            (food.nutritionValue * 100).toInt()
                        )
                    })"
                )
            }
            }

            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = modifier.fillMaxWidth()
            ) {
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
}

@Composable
@Preview(name = "FoodMenuScreenPreview")
fun FoodMenuScreenPreview() {
    FoodMenuScreen(
        onBack = {},
    )
}