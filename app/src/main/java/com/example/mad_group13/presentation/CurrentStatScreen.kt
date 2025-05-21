package com.example.mad_group13.presentation
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentStatScreen(onBackToMain: ()-> Unit, modifier: Modifier = Modifier){

    val placeholders = listOf(
        "Health" to 0.9f,
        "Happiness" to 0.5f,
        "Hunger" to 0.7f,
        "Activity" to 0.4f,
    )
    //TODO: create Pet data structure

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Current Pet Stats") }
            )
        },
        bottomBar = { Button(
            modifier = modifier.fillMaxWidth().padding(3.dp),
            onClick = onBackToMain,
            content = { Text("Back to Main Menu") }
        )},
        modifier = modifier){ padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier.padding(padding)){
            LazyRow() {
                items(placeholders) { (name, value) -> StatDisplay(name, value) }
            }
        }
    }
}

@Composable
fun StatDisplay(statName: String, statValue: Float, modifier: Modifier = Modifier){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(2.dp)
    ){
        Text(
            text = statName,
            modifier = modifier.padding(2.dp)
            )
        Box(contentAlignment = Alignment.Center,
            modifier = modifier.size(50.dp)
            ){
            CircularProgressIndicator(
                progress = { statValue },
                modifier = modifier.fillMaxSize()
            )
            Text(
                text = "${(statValue*100).toInt()}%",
            )
        }

    }
}


@Preview(name = "ActivePetStatsPreview")
@Composable
fun ActivePetStatsPreview(){
        CurrentStatScreen(
            onBackToMain = { },
            modifier = Modifier
        )
}
