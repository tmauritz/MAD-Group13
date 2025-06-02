package com.example.mad_group13.presentation.minigame.numberguess

import android.util.Log

class GameLogic(initialDigitsToGuess: Int = DEFAULT_DIGITS) {
    companion object {
        val DEFAULT_DIGITS: Int = 4
        fun generateRandomNumber(digits: Int): List<Int> {
            val secretNumber = (1..9).shuffled().take(digits)
            Log.i("MAD_Number", "number to guess: $secretNumber...ssssshhhhhh")
            return secretNumber
        }
    }

    var digitsToGuess: Int = initialDigitsToGuess
        private set
    private var targetNumber: List<Int> = generateRandomNumber(digitsToGuess)

    val evaluateGuess: (String) -> GuessResult = { guess ->
        val guessList = guess.map { it.digitToInt() }
        val correctDigits = guessList.intersect(targetNumber).count()
        val correctPositions = guessList.zip(targetNumber).count { it.first == it.second }
        GuessResult(guess, correctDigits, correctPositions)
    }

    val isValidGuess: (String?) -> Boolean = { guess ->
        guess != null &&
                guess.all { it.isDigit() && it.digitToInt() in (1..9) } &&
                guess.map { it.digitToInt() }.distinct().size == targetNumber.size
    }

    fun isWinningGuess(guess: GuessResult): Boolean = guess.toString() == "$digitsToGuess:$digitsToGuess"

    fun regenerateTargetNumber(){
        targetNumber = generateRandomNumber(digitsToGuess)
    }

}
