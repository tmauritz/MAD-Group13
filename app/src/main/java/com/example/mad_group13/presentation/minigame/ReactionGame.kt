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
    var gameFinished by remember { mutableStateOf(false) }
    var lastResultWasWin by remember { mutableStateOf(false) }

    LaunchedEffect(startTimer) {
        if (startTimer) {
            delay(3000)
            boxColor = Color.Green
            readyToTap = true
            message = "Tap now!"
            startTime = System.currentTimeMillis()
            startTimer = false
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

            if (gameFinished) {
                Button(onClick = {
                    if (lastResultWasWin) onWin() else onLoss()
                    // Reset game state
                    gameStarted = false
                    gameFinished = false
                    message = "Press Start to begin!"
                }) {
                    Text("Close")
                }
            } else {
                if (!gameStarted) {
                    Button(onClick = {
                        gameStarted = true
                        message = "Wait for green..."
                        boxColor = Color.Red
                        readyToTap = false
                        startTimer = true
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
                                    message = "You pressed too early! You lose."
                                    lastResultWasWin = false
                                    gameFinished = true
                                } else {
                                    val reactionTime = System.currentTimeMillis() - startTime
                                    val seconds = reactionTime / 1000f
                                    when {
                                        seconds < 0.01f -> {
                                            message = "Whoa! Too fast, maybe cheating? You lose."
                                            lastResultWasWin = false
                                        }
                                        seconds <= 0.5f -> {
                                            message = "You pressed in time! Reaction: ${"%.3f".format(seconds)}s. You win!"
                                            lastResultWasWin = true
                                        }
                                        else -> {
                                            message = "You pressed too late! Reaction: ${"%.3f".format(seconds)}s. You lose."
                                            lastResultWasWin = false
                                        }
                                    }
                                    gameFinished = true
                                }
                            }
                    )
                }
            }
        }
    }
}
