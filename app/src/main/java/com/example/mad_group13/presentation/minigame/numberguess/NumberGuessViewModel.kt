package com.example.mad_group13.presentation.minigame.numberguess

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mad_group13.presentation.minigame.MinigameResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NumberGuessViewModel: ViewModel(){

    val gameLogic = GameLogic()
    private var _previousGuesses = MutableStateFlow<List<GuessResult>>(emptyList())
    private var _minigameResult = MutableStateFlow(MinigameResult.IN_PROGRESS)

    val previousGuesses: StateFlow<List<GuessResult>> = _previousGuesses.asStateFlow()
    val minigameResult: StateFlow<MinigameResult> = _minigameResult.asStateFlow()

    fun evaluateUserGuess(userGuess: String){
        Log.i("MAD_Numbers", "userguess: $userGuess")
        if (gameLogic.isValidGuess(userGuess)) {
            Log.i("MAD_Numbers", "Valid Guess!")
            val guessResult = userGuess.let { gameLogic.evaluateGuess(it) }
            _previousGuesses.value += guessResult
            if (gameLogic.isWinningGuess(guessResult)){
                _minigameResult.value = MinigameResult.WIN
            }
        }else{
            Log.i("MAD_Numbers", "invalidGuess")
        }
    }

    fun isValidGuess(userGuess: String): Boolean{
        return gameLogic.isValidGuess(userGuess)
    }

    fun resetMinigame(){
        _previousGuesses.value = emptyList()
        _minigameResult.value = MinigameResult.IN_PROGRESS
        gameLogic.regenerateTargetNumber()
    }

}