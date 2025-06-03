package com.example.mad_group13.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pet( //CHANGING ANYTHING HERE MEANS INCREMENTING VERSION NUMBER IN PetDB!
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nickname: String = "My Adorable Diamond",
    val maturity: PetMaturity = PetMaturity.BABY,
    val age: Float = 0f,
    val type: Int = 0,
    val sickness: Boolean = false,
    val sicknessTimestamp: Long = 0L,
    val lastChecked: Long = System.currentTimeMillis(),
    var health: Float = 1f,
    var happiness: Float = 0.5f,
    var hunger: Float = 0.2f,
    var activity: Float = 0.5f,
    val activityTimestamp: Long = 0L
    ){
    init {
        // Coerce initial values to keep between 0 and 100%
        health = health.coerceIn(0f, 1f)
        happiness = happiness.coerceIn(0f, 1f)
        hunger = hunger.coerceIn(0f, 1f)
        activity = activity.coerceIn(0f, 1f)
    }
}

fun getPetName(): String = listOf(
    "Glitterpebble",
    "Shinobrite",
    "Crystarbo",
    "Quartzina Turner",
    "Rocky Balquartz",
    "Gemothy Chalamet",
    "Sir Sparkalot",
    "Facetious",
    "Shardi B",
    "Opal Winfrey",
    "Mistress Sparklebutt"
).shuffled().first()

