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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mad_group13.R
import com.example.mad_group13.presentation.minigame.numberguess.GuessResult
import com.example.mad_group13.presentation.minigame.numberguess.NumberGuessViewModel

@Composable
fun NumberGuessingGame(onWin: ()-> Unit, onLoss: ()->Unit, numberGuessViewModel: NumberGuessViewModel = hiltViewModel()){

    var userGuessState by remember { mutableStateOf(TextFieldValue("1234")) }
    val previousGuessList by numberGuessViewModel.previousGuesses.collectAsState()
    val minigameResultState = numberGuessViewModel.minigameResult.collectAsState()

    Card(modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)) {
        Column(modifier = Modifier.padding(5.dp), horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = stringResource(R.string.minigame_number_description), textAlign = TextAlign.Center)

            Text(text = stringResource(R.string.minigame_number_guesses_so_far), modifier = Modifier.padding(top = 10.dp))
            LazyColumn(modifier = Modifier.height(200.dp)){ items(previousGuessList){ guess -> GuessResultItem(guess) } }

            HorizontalDivider()
            OutlinedTextField(
                value = userGuessState,
                onValueChange = {val filtered = it.text.filter { char -> char.isDigit() }.take(4)
                    userGuessState = it.copy(text = filtered, selection = TextRange(filtered.length))},
                label = { Text(stringResource(R.string.minigame_number_your_guess)) },
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
                            Text(stringResource(R.string.minigame_number_break_lock))
                        }
                        Button(onClick = { numberGuessViewModel.evaluateUserGuess(userGuessState.text) }) {
                            Text(stringResource(R.string.minigame_number_guess_button))
                        }
                    }
                MinigameResult.WIN -> {
                    Text(stringResource(R.string.minigame_number_win))
                    Button(onClick = {
                        onWin()
                    }) {
                        Text(stringResource(R.string.button_back))
                    }
                }
                MinigameResult.LOSS -> {
                    Text(stringResource(R.string.minigame_number_loss))
                    Button(onClick = {
                        onWin()
                    }) {
                        Text(stringResource(R.string.button_back))
                    }
                }
            }
        }
    }
}

@Composable
fun GuessResultItem(guessResult: GuessResult){
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()){
        Text(text = guessResult.guess)
        Text(text = stringResource(R.string.minigame_number_correct_digits, guessResult.correctDigits))
        Text(text = stringResource(R.string.minigame_number_correct_positions, guessResult.correctPositions))
    }
}

@Preview(name="NumberGuessPreview")
@Composable
fun NumberGuessingGamePreview(){
    NumberGuessingGame({}, {})
}