package com.example.mad_group13.presentation.minigame.numberguess

import kotlin.system.exitProcess

object GameManager {
    private val gameLogic = GameLogic()

    fun start() {
        println(
            "Welcome to the Number Guessing Game!\n" +
                    "Guess a 4-digit number with unique digits."
        )
        while (true){
            val userGuess = getUserGuess()
            if(userGuess == null) exitGame()
            else {
                if (gameLogic.isValidGuess(userGuess)) {
                    val guessResult = userGuess.let { gameLogic.evaluateGuess(it) }
                    println(guessResult)
                    if (gameLogic.isWinningGuess(guessResult)){
                        println("Congratulations! You guessed the correct number.")
                        promptRestart()
                        break
                    }
                } else println("Invalid guess.")
            }
        }
    }

    private fun getUserGuess(): String? {
        println("Enter your guess (${gameLogic.digitsToGuess} unique digits) or type 'exit': ")
        val userGuess = readln()
        return if (userGuess == "exit") {
            null
        } else {
            userGuess
        }
    }



    private fun promptRestart() {
        print("Do you want to play again? (y/n): ")
        while(true){
            when (readln()) {
                "n" -> {
                    exitGame()
                    break
                }
                "y" -> {
                    gameLogic.regenerateTargetNumber()
                    start()
                    break
                }
                else -> {
                    println("[y/n]")}
            }
        }
    }

    private fun exitGame() {
        println("Thanks for playing!")
        exitProcess(0)
    }

}
