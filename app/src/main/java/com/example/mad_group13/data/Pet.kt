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
    val health: Float = 1f,
    val happiness: Float = .5f,
    val hunger: Float = .2f,
    val activity: Float = .5f,
    val lastChecked: Long = System.currentTimeMillis(),
)

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
    "Opal Winfrey"
).shuffled().first()

