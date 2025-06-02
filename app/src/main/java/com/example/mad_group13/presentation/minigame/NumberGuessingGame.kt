package com.example.mad_group13.presentation.minigame

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mad_group13.presentation.minigame.numberguess.GuessResult
import com.example.mad_group13.presentation.minigame.numberguess.NumberGuessViewModel

@Composable
fun NumberGuessingGame(onWin: ()-> Unit, onLoss: ()->Unit, numberGuessViewModel: NumberGuessViewModel = hiltViewModel()){

    var userGuessState by remember { mutableStateOf(TextFieldValue("1234")) }
    val previousGuessList by numberGuessViewModel.previousGuesses.collectAsState()
    val minigameResultState = numberGuessViewModel.minigameResult.collectAsState()

    Card(modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)) {
        Column(modifier = Modifier.padding(5.dp), horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = "Your Pet has locked you out! Guess a 4-digit number with unique digits, no zeroes.", textAlign = TextAlign.Center)

            Text(text = "Guesses so far:", modifier = Modifier.padding(top = 10.dp))
            LazyColumn(modifier = Modifier.height(200.dp)){ items(previousGuessList){ guess -> GuessResultItem(guess) } }

            HorizontalDivider()
            OutlinedTextField(
                value = userGuessState,
                onValueChange = {val filtered = it.text.filter { char -> char.isDigit() }.take(4)
                    userGuessState = it.copy(text = filtered, selection = TextRange(filtered.length))},
                label = { Text("Your Guess:") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {numberGuessViewModel.evaluateUserGuess(userGuessState.text)}),
                singleLine = true,
                isError = !numberGuessViewModel.isValidGuess(userGuessState.text)
            )

            when(minigameResultState.value){
                MinigameResult.IN_PROGRESS ->
                    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(onClick = onLoss) {
                            Text("Break the Lock...")
                        }
                        Button(onClick = { numberGuessViewModel.evaluateUserGuess(userGuessState.text) }) {
                            Text("Guess Digits!")
                        }
                    }
                MinigameResult.WIN -> {
                    Text("You win!")
                    Button(onClick = {
                        onWin()
                    }) {
                        Text("Close")
                    }
                }
                MinigameResult.LOSS -> Button(onClick = {numberGuessViewModel.evaluateUserGuess(userGuessState.text)}) {
                    Text("Guess")
                }
            }
        }
    }
}

@Composable
fun GuessResultItem(guessResult: GuessResult){
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()){
        Text(text = guessResult.guess)
        Text(text = "Digits: ${guessResult.correctDigits}")
        Text(text = "Positions: ${guessResult.correctPositions}")
    }
}

@Preview(name="NumberGuessPreview")
@Composable
fun NumberGuessingGamePreview(){
    NumberGuessingGame({}, {})
}