package com.example.mad_group13.presentation.viewModel

import androidx.compose.runtime.Composable
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mad_group13.data.PetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetHistoryViewModel @Inject constructor(
    private val petRepository: PetRepository
): ViewModel(), DefaultLifecycleObserver {

    private val _petHistoryState = MutableStateFlow(PetHistoryModel())
    val petHistoryState: MutableStateFlow<PetHistoryModel> = _petHistoryState

    init {
        viewModelScope.launch {
            _petHistoryState.update { petHistoryState.value.copy(
                    petList = petRepository.getPetHistory()
                )
            }
        }
    }
}