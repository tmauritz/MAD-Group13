package com.example.mad_group13.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.mad_group13.presentation.viewModel.PetStateViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigatePetHistory: () -> Unit,
    modifier: Modifier = Modifier,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    petStateViewModel: PetStateViewModel = hiltViewModel()
) {

    val activePet by petStateViewModel.petState.collectAsState()

    DisposableEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(petStateViewModel)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(petStateViewModel)
        }
    }

    //TODO: these don't belong here i guess?
    var showNicknameDialog by remember { mutableStateOf(false) }
    var isFeeding by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My adorable Diamond!") }
            )
        }

    ) { innerPadding ->
        Box (
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ){

            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row (
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.fillMaxWidth()
                ){
                    Button(
                        onClick = { petStateViewModel.fullRestoreActivePet() }
                    ) {
                        Text("Pokecenter Heal")
                    }
                    Button(
                        onClick = { petStateViewModel.retirePetAndStartNew() }
                    ) {
                        Text("Retire(final)!")
                    }
                    Button(
                        onClick = {
                            showNicknameDialog = true
                        }
                    ) {
                        Text("Nickname")
                    }
                }

                Text(text = activePet.nickname,
                modifier = modifier.padding(top = 10.dp))

                Row {
                    Image(
                    painter = painterResource(id = petStateViewModel.getDrawableID()),
                    contentDescription = "A pretty diamond!",
                    modifier = modifier
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = modifier.fillMaxWidth()){
                    StatDisplay("Health", activePet.health)
                    StatDisplay("Hunger", activePet.hunger)
                    StatDisplay("Happiness", activePet.happiness)
                    StatDisplay("Activity", activePet.activity)
                }

                Row (
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom,
                    modifier = modifier.fillMaxSize()
                ){
                    Button(
                        onClick = { petStateViewModel.updatePetState(waitForTimeInterval = false) }
                    ) {
                        Text("Pass Time")
                    }
                    Button(
                        onClick = {
                            petStateViewModel.feedPet() }
                    ) {
                        Text("Feed")
                    }
                    Button(
                        onClick = onNavigatePetHistory
                    ) {
                        Text("History")
                    }
                }
            }
            if (showNicknameDialog) {
                NicknameDialog(
                    onDismiss = { showNicknameDialog = false },
                    onConfirm = { newName ->
                        petStateViewModel.changePetNickname(newName)
                        showNicknameDialog = false
                    }
                )
            }
            if (isFeeding) {
                LaunchedEffect(Unit) {
                    // TODO: changing it later with the animation or real logic
                    kotlinx.coroutines.delay(2000)
                    isFeeding = false
                }
            }
        }


    }

}


@Preview(name = "MainScreenPreview")
@Composable
fun MainScreenPreview(){
    MainScreen({})
}