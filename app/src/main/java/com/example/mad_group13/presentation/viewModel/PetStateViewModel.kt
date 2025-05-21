package com.example.mad_group13.presentation.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mad_group13.model.PetState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PetStateViewModel(): ViewModel() {
    private val _petState = MutableStateFlow(PetState())
    val petState: StateFlow<PetState> = _petState

    fun feedPet() {
        // TODO: later changing pet-state (lower hunger)
        println("Pet is being fed!") // Nur als Platzhalter
    }

    fun changePetNickname(newName: String) {
        // TODO: save pet-name (in ViewModel or SharedState)
        println("Pet's new nickname is: $newName") // placeholder
    }

}