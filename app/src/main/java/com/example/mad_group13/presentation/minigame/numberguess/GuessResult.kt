package com.example.mad_group13.presentation.minigame.numberguess

data class GuessResult(val guess: String, val correctDigits: Int, val correctPositions: Int) {
    override fun toString(): String {
        return "$correctDigits:$correctPositions"
    }
}
