package com.example.mad_group13.presentation.minigame

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NumberGuessingGame(onWin: ()-> Unit, onLoss: ()->Unit){
    Card(modifier = Modifier.fillMaxSize().padding(top = 20.dp, bottom = 20.dp)) {
        Column(modifier = Modifier.padding(5.dp)){
            //TODO: implement proper Minigame
            Text("You Win! Or lose, depending on what you want to test... ")
            Row {
                Button(onWin) { Text("Press to Win ") }
                Button(onLoss) { Text("Press to Lose ") }
            }
        }
    }
}