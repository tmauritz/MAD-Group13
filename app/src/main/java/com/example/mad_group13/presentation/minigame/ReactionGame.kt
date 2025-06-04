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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mad_group13.R
import kotlinx.coroutines.delay

@Composable
fun ReactionGame(
    onWin: () -> Unit,
    onLoss: () -> Unit
) {
    val context = LocalContext.current
    var gameStarted by remember { mutableStateOf(false) }
    var gameFinished by remember { mutableStateOf(false) }
    var readyToTap by remember { mutableStateOf(false) }
    var lastResultWasWin by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf(context.getString(R.string.reaction_start_message)) }
    var startTime by remember { mutableStateOf(0L) }
    var lightStates by remember { mutableStateOf(List(5) { Color.Gray }) }

    LaunchedEffect(gameStarted) {
        if (gameStarted) {
            message = context.getString(R.string.reaction_lights_countdown)
            // painting red one after another
            for (i in 0 until 5) {
                delay(500)
                lightStates = lightStates.toMutableList().also { it[i] = Color.Red }
            }
            // After short delay --> green
            delay(3000)
            lightStates = List(5) { Color.Green }
            message = context.getString(R.string.reaction_tap_now)
            readyToTap = true
            startTime = System.currentTimeMillis()
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

            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                lightStates.forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .padding(4.dp)
                            .background(color)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (gameFinished) {
                Button(onClick = {
                    if (lastResultWasWin) onWin() else onLoss()
                    // Reset
                    gameStarted = false
                    gameFinished = false
                    lightStates = List(5) { Color.Gray }
                    message = context.getString(R.string.reaction_start_message)
                    readyToTap = false
                }) {
                    Text(stringResource(R.string.reaction_close_button))
                }
            } else {
                if (!gameStarted) {
                    Button(onClick = {
                        gameStarted = true
                    }) {
                        Text(stringResource(R.string.reaction_start_button))
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .background(Color.LightGray)
                            .clickable {
                                if (!readyToTap) {
                                    message = context.getString(R.string.reaction_too_early)
                                    lastResultWasWin = false
                                } else {
                                    val reactionTime = System.currentTimeMillis() - startTime
                                    val seconds = reactionTime / 1000f
                                    when {
                                        seconds < 0.01f -> { //Is there that the player is not predicting, when the lights turn off, if should be REACTION
                                            message = context.getString(R.string.reaction_too_fast)
                                            lastResultWasWin = false
                                        }
                                        seconds <= 0.5f -> {
                                            message = context.getString(R.string.reaction_in_time, seconds)
                                            lastResultWasWin = true
                                        }
                                        else -> {
                                            message = context.getString(R.string.reaction_too_late, seconds)
                                            lastResultWasWin = false
                                        }
                                    }
                                }
                                gameFinished = true
                            }
                    )
                }
            }
        }
    }
}
