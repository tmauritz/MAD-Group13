package com.example.mad_group13.presentation

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.mad_group13.presentation.viewModel.PetHistoryModel
import com.example.mad_group13.presentation.viewModel.PetHistoryViewModel
import com.example.mad_group13.ui.theme.MADGroup13Theme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetHistory(onBackNavigation: () -> Unit,
               modifier: Modifier = Modifier,
               lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
               petHistoryViewModel: PetHistoryViewModel = hiltViewModel()
){
    val petHistory by petHistoryViewModel.petHistoryState.collectAsState()

    DisposableEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(petHistoryViewModel)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(petHistoryViewModel)
        }
    }

    Scaffold(modifier = modifier,
        topBar = {
            TopAppBar(
                title = {Text("The Rubble Dump")}
            )},
        bottomBar = {
        Row (modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
            Button(
                onClick = onBackNavigation,
            )
        { Text("Back to Main Screen") } }
    }){ paddingValues ->
        LazyColumn(modifier = modifier.padding(paddingValues)) {
            items(petHistory.petList){pet -> PetHistoryRowItem(
                name = pet.nickname,
                age = pet.age,
                happiness = pet.happiness,
                hunger = pet.hunger,
                activity = pet.activity,
                modifier = modifier
            ) }
        }
    }
}

@Composable
fun PetHistoryRowItem(name: String,
                      age: Float,
                      happiness: Float,
                      hunger: Float,
                      activity: Float,
                      modifier: Modifier = Modifier) {
    Row (modifier = modifier.padding(5.dp)){
        Text( textAlign = TextAlign.Center,
            text = "$name reached an age of $age, " +
            "was last seen ${(happiness*100).toInt()}% happy, " +
            "${((1f-hunger)*100).toInt()}% hungry and " +
            "${(activity*100).toInt()}% active.")
    }
}