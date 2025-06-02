package com.example.mad_group13.presentation.minigame

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun ReactionGame(
    onWin: () -> Unit,
    onLoss: () -> Unit
) {
    var boxColor by remember { mutableStateOf(Color.Red) }
    var gameStarted by remember { mutableStateOf(false) }
    var readyToTap by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("Press Start to begin!") }
    var startTime by remember { mutableStateOf(0L) }
    var startTimer by remember { mutableStateOf(false) }

    // This LaunchedEffect runs only when startTimer becomes true
    LaunchedEffect(startTimer) {
        if (startTimer) {
            delay(3000)
            boxColor = Color.Green
            readyToTap = true
            message = "Tap now!"
            startTime = System.currentTimeMillis()
            startTimer = false  // reset trigger
        }
    }

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = message)

            Spacer(modifier = Modifier.height(20.dp))

            if (!gameStarted) {
                Button(onClick = {
                    gameStarted = true
                    message = "Wait for green..."
                    boxColor = Color.Red
                    readyToTap = false
                    startTimer = true  // trigger LaunchedEffect
                }) {
                    Text("Start Game")
                }
            } else {
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .background(boxColor)
                        .clickable {
                            if (!readyToTap) {
                                message = "Too early! You lose."
                                onLoss()
                                gameStarted = false
                            } else {
                                val reactionTime = System.currentTimeMillis() - startTime
                                val seconds = reactionTime / 1000f
                                if (seconds < 0.01f) {
                                    message = "Too fast! Did you cheat?"
                                    onLoss()
                                } else if (seconds <= 0.5f) {
                                    message = "You win! Reaction time: ${"%.3f".format(seconds)}s"
                                    onWin()
                                } else {
                                    message = "Too slow! You lose."
                                    onLoss()
                                }
                                gameStarted = false
                            }
                        }
                )
            }
        }
    }
}
