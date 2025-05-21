package com.example.mad_group13.presentation.viewModel
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mad_group13.data.Pet
import com.example.mad_group13.data.PetRepository
import com.example.mad_group13.logic.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Float.min
import javax.inject.Inject
import kotlin.math.max

@HiltViewModel
class PetStateViewModel @Inject constructor(
    private val petRepository: PetRepository
): ViewModel(), DefaultLifecycleObserver {
    private val _petState = MutableStateFlow(Pet())
    val petState: StateFlow<Pet> = _petState

    init{
        viewModelScope.launch {
            Log.i("MAD", "Pulling Pet from Storage...")
            val activePet: Pet = petRepository.getActivePet()
            _petState.update { activePet }
            petState
                .drop(1)
                .distinctUntilChanged()
                .collect{ pet ->
                    petRepository.updatePet(pet)
                }
            updatePetState()
        }
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Log.i("QuestionViewModel","OnStart")
    }

    fun feedPet() {
        // TODO: later changing pet-state (lower hunger)
        println("Pet is being fed!") // Nur als Platzhalter
    }

    fun changePetNickname(newName: String) {
        // TODO: save pet-name
        println("Pet's new nickname is: $newName") // placeholder
    }

    fun updatePetState(waitForTimeInterval: Boolean = true){
        val now = System.currentTimeMillis()
        if(waitForTimeInterval && now - Constants.UPDATE_TIME_INTERVAL < petState.value.lastChecked) return //only update after time interval

        //TODO: figure out maths of hunger per hour etc, for now every time function is called we lose one hour's worth
        _petState.update { pet -> pet.copy(
            health = if (pet.hunger == 1f) max(pet.health - .1f, 0f) else pet.health,
            hunger = min(pet.hunger + .05f , 1f), //TODO: full hunger or empty hunger? Requirements say "when the pet is left alone, hunger and activity meters are reduced by 5 points every hour"
            activity = max(pet.activity - .05f,0f)
            )
        }
    }

}