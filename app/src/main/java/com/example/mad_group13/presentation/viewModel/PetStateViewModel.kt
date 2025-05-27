package com.example.mad_group13.presentation.viewModel
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mad_group13.R
import com.example.mad_group13.data.Pet
import com.example.mad_group13.data.PetMaturity
import com.example.mad_group13.data.PetRepository
import com.example.mad_group13.logic.Constants
import com.example.mad_group13.logic.nutrition.Food
import com.example.mad_group13.logic.nutrition.Snack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.max

@HiltViewModel
class PetStateViewModel @Inject constructor(
    private val petRepository: PetRepository
): ViewModel(), DefaultLifecycleObserver {
    private val _petState = MutableStateFlow(Pet())
    val petState: StateFlow<Pet> = _petState

    private var petUpdateCycle: Job? = null

    init{
        viewModelScope.launch {
            Log.i("MAD_Pet", "Pulling Pet from Storage...")
            val activePet: Pet = petRepository.getActivePet()
            _petState.update { activePet }
            petState.collect{ pet -> //automatically save pet to DB when changes are made
                Log.i("MAD_Pet_Update","Saving Pet to DB... $pet")
                petRepository.updatePet(pet)
            }
        }
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Log.i("MAD_PetStateViewModel","OnStart")
        startPetUpdateCycle()
    }

    private fun startPetUpdateCycle(){
        stopPetUpdateCycle() //cancel any existing countdown
        petUpdateCycle = viewModelScope.launch {
            while (true){
                delay(Constants.UPDATE_INTERVAL_MS)
                Log.i("MAD_Pet_Update", "Auto-Updating Pet")
                updatePetState(waitForTimeInterval = false)

            }
        }
    }

    private fun stopPetUpdateCycle(){
        petUpdateCycle?.cancel()
    }

    fun feedPet(food: Food) {
        _petState.update { pet ->
            food.feed(pet)
        }
    }

    fun feedPet(snack: Snack){
        _petState.update { pet ->
            snack.feed(snack.applyEffect(pet))
        }
    }

    fun reduceHappinessBy(amount: Float) {
        _petState.update { pet ->
            val newHappiness = (pet.happiness - amount).coerceAtLeast(0f)
            pet.copy(happiness = newHappiness)
        }
    }

    fun increaseHappinessBy(amount: Float) {
        _petState.update { pet ->
            val newHappiness = (pet.happiness + amount).coerceAtLeast(0f)
            pet.copy(happiness = newHappiness)
        }
    }

    fun changePetNickname(newName: String) {
        _petState.update { pet -> pet.copy(nickname = newName) }
    }

    fun retirePetAndStartNew(){
        viewModelScope.launch {
            Log.i("MAD_Pet_Retirement", "Retiring Pet ${petState.value}")
            _petState.update { petRepository.getNewPet() }
            Log.i("MAD_Pet_Retirement", "New Pet Id ${petState.value.id}")
        }
    }

    fun fullRestoreActivePet(){
        _petState.update { pet -> pet.copy(
            health = 1f,
            hunger = 1f,
            activity = 1f,
            lastChecked = System.currentTimeMillis()
            )
        }
    }

    //for Debuging: should be deleted before project completion
    fun reduceHungerBy(amount: Float) {
        _petState.update { pet ->
            val newHunger = (pet.hunger - amount).coerceAtLeast(0f)
            pet.copy(hunger = newHunger)
        }
    }

    fun getDrawableID(): Int {
        return when(petState.value.type) {
            0 -> R.drawable.dia_0_baby
            1 -> R.drawable.dia_1_teen    // TODO: Add more artwork :B
            2 -> R.drawable.dia_2_adult
            3 -> R.drawable.dia_20_purple
            else -> R.drawable.dia_1_teen
        }
    }

    private fun updatePetMaturity(age: Float) {
        if (age >= Constants.PET_MATURITY_ADULT) {
            _petState.update { pet -> pet.copy(maturity = PetMaturity.ADULT) }
            Log.i("MAD_Pet_Maturity","Teen becomes Adult")
            setPetType()
            fullRestoreActivePet()
        }
        else if (age >= Constants.PET_MATURITY_TEEN && _petState.value.maturity < PetMaturity.TEEN) {
            _petState.update { pet -> pet.copy(
                maturity = PetMaturity.TEEN,
                type = 1
            ) }
            Log.i("MAD_Pet_Maturity","Baby becomes Teen")
            //setPetType()
            fullRestoreActivePet()
        }

        Log.i("MAD_Pet_Maturity", _petState.value.maturity.toString())
    }

    private fun setPetType() {
        // TODO: Set values for specific types
        if (petState.value.hunger <= 0.5 && petState.value.happiness >= 0.5) {
            _petState.update { pet -> pet.copy(type = 1) }
        } else {
            _petState.update { pet -> pet.copy(type = 2) }
        }
    }

    private fun setSickness() {
        _petState.update { pet -> pet.copy(
            sickness = true,
            sicknessTimestamp = System.currentTimeMillis()
        )
        }
    }

    fun updatePetState(waitForTimeInterval: Boolean = true){
        // get current time and last checked time
        val nowMillis = System.currentTimeMillis()
        val lastCheckedMillis = petState.value.lastChecked
        if(waitForTimeInterval && nowMillis - Constants.UPDATE_INTERVAL_MS < petState.value.lastChecked) return //only update after time interval

        Log.i("MAD_Pet_Update", "Updating Pet...")
        // get time interval since last change
        val timeDiffMillis = nowMillis - lastCheckedMillis

        // update age, in seconds
        val updatedAge = _petState.value.age + (timeDiffMillis / 1000).toFloat()
        // update maturity, if applicable
        if (_petState.value.maturity < PetMaturity.ADULT) { updatePetMaturity(updatedAge) }
        // random chance for pet to get sick
        if ((0..100).random() < 10) { setSickness() }

        val relativeHungerLoss = Constants.PET_HUNGER_LOSS_PER_INTERVAL * (timeDiffMillis / Constants.UPDATE_INTERVAL_MS)
        val relativeActivityLoss = Constants.PET_ACTIVITY_LOSS_PER_INTERVAL *  (timeDiffMillis / Constants.UPDATE_INTERVAL_MS)
        // update hunger and Activity
        val updatedHunger = max(_petState.value.hunger - relativeHungerLoss , 0f)
        val updatedActivity = max(_petState.value.activity - relativeActivityLoss,0f)
        // calculate health loss if hunger is at 0
        // find time interval where health loss starts
        val timeIntervalsUntilEmptyHunger = _petState.value.hunger / Constants.PET_HUNGER_LOSS_PER_INTERVAL
        // get time intervals left
        val timeIntervalsOfHealthLoss = timeDiffMillis - (Constants.UPDATE_INTERVAL_MS * timeIntervalsUntilEmptyHunger)
        val relativeHealthLoss = Constants.PET_HEALTH_LOSS_PER_INTERVAL * timeIntervalsOfHealthLoss
        // update Health
        val updatedHealth = if (timeIntervalsOfHealthLoss > 0f) max(_petState.value.health - relativeHealthLoss, 0f) else _petState.value.health

        Log.i("MAD_Pet_Update", "Pet Update for Pet ${_petState.value.id}: new Age: $updatedAge, new Hunger: $updatedHunger, new Health: $updatedHealth, new Activity: $updatedActivity, last checked: $nowMillis")
        //update pet
        _petState.update { pet -> pet.copy(
            age = updatedAge,
            health = updatedHealth,
            hunger = updatedHunger,
            activity = updatedActivity,
            lastChecked = nowMillis
            )
        }
    }

    fun increaseActivity(amount: Float) {
        _petState.update { pet -> pet.copy(
                activity = pet.activity + amount
            )
        }
    }

}