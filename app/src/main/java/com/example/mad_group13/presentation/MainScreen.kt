package com.example.mad_group13.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.mad_group13.R
import com.example.mad_group13.logic.Constants
import com.example.mad_group13.presentation.minigame.MinigameSelector
import com.example.mad_group13.presentation.minigame.NumberGuessingGame
import com.example.mad_group13.presentation.minigame.ReactionGame
import com.example.mad_group13.presentation.viewModel.PetStateViewModel
import com.example.mad_group13.presentation.viewModel.StockViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigatePetHistory: () -> Unit,
    modifier: Modifier = Modifier,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    petStateViewModel: PetStateViewModel = hiltViewModel(),
    stockViewModel: StockViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        stockViewModel.onTeslaPriceChanged = { percentageChange ->
            if (percentageChange > 0) {
                petStateViewModel.reduceHappinessBy(percentageChange / 100f)
            } else {
                petStateViewModel.increaseHappinessBy(-percentageChange / 100f)
            }
        }
    }

    val activePet by petStateViewModel.petState.collectAsState()
    val teslaPrice by stockViewModel.teslaPrice.collectAsState()


    DisposableEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(petStateViewModel)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(petStateViewModel)
        }
    }


    //TODO: these don't belong here i guess? idk where to put them though
    var showNicknameDialog by remember { mutableStateOf(false) }
    var showFoodMenu by remember { mutableStateOf(false) }
    var isFeeding by remember { mutableStateOf(false) }
    var activeMinigame by remember { mutableStateOf(MinigameSelector.NONE) }
    var showRetireDialog by remember { mutableStateOf(false) }
    var showMinigameSelection by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                actions = {
                    MainMenu(
                        mainMenuItems = listOf(
                            MainMenuItem(stringResource(R.string.button_history), onNavigatePetHistory),
                            MainMenuItem(stringResource(R.string.button_retire), {showRetireDialog = true})
                            )
                    )
                }
            )
        }

    ) { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            Image(
                painter = painterResource(id = petStateViewModel.getDrawableID()),
                contentDescription = "A pretty diamond!",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)

            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = modifier
                        .padding(innerPadding)
                        .fillMaxWidth()){
                    StatDisplay(stringResource(R.string.label_stat_health), activePet.health)
                    StatDisplay(stringResource(R.string.label_stat_hunger), activePet.hunger)
                    StatDisplay(stringResource(R.string.label_stat_happiness), activePet.happiness)
                    StatDisplay(stringResource(R.string.label_stat_activity), activePet.activity)
                }
            }

            Column{
                Text(
                    text = activePet.nickname,
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .padding(bottom = 20.dp)
                        .fillMaxWidth()
                        .clickable {
                            showNicknameDialog = true
                        }
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icons_heal),
                        contentDescription = "Ding ding di-di-ding!!",
                        modifier = Modifier
                            .width(70.dp)
                            .clickable {
                                petStateViewModel.fullRestoreActivePet()
                            }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.icons_snack),
                        contentDescription = "Such a snack!",
                        modifier = Modifier
                            .width(70.dp)
                            .clickable {
                                Log.i("MAD_MainScreen_image", "Se snack hath been clicked")
                            }
                    )
                    Button(
                        onClick = { showFoodMenu = true }  //onNavigateToFoodMenu
                    ) {
                        Text(stringResource(R.string.button_feed))
                    }

                    Image(
                        painter = painterResource(id = R.drawable.icons_minigames),
                        contentDescription = "Let's playyy",
                        modifier = Modifier
                            .width(70.dp)
                            .clickable {
                                showMinigameSelection = true
                            }
                    )

                    // Debug stocks:
                    /*Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Text(text = "TSLA: $teslaPrice")
                        Button(
                            onClick = { stockViewModel.fetchTeslaStock() },
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            Text("STOCKS")
                        }
                    }*/
                }
            }
        }

        if(activeMinigame != MinigameSelector.NONE) {
            Dialog(onDismissRequest = { activeMinigame = MinigameSelector.NONE }) {
                val onWin = {
                    petStateViewModel.increaseHappinessBy(.2f)
                    petStateViewModel.increaseActivity(.2f)
                    activeMinigame = MinigameSelector.NONE
                }
                val onLoss = {
                    petStateViewModel.reduceHappinessBy(.2f)
                    petStateViewModel.increaseActivity(.2f)
                    activeMinigame = MinigameSelector.NONE
                }
                when (activeMinigame) {
                    MinigameSelector.NUMBERGUESS -> NumberGuessingGame(onWin, onLoss)
                    MinigameSelector.REACTION -> ReactionGame(onWin, onLoss)
                    MinigameSelector.MEMORY -> {}
                    MinigameSelector.NONE -> {}
                }
            }
        }

        if (showMinigameSelection) {
            Dialog(onDismissRequest = { showMinigameSelection = false }) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Choose a Minigame")
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(onClick = {
                        activeMinigame = MinigameSelector.NUMBERGUESS
                        showMinigameSelection = false
                    }) {
                        Text("Number Guessing Game")
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(onClick = {
                        activeMinigame = MinigameSelector.REACTION
                        showMinigameSelection = false
                    }) {
                        Text("Reaction Game")
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(onClick = {
                        activeMinigame = MinigameSelector.MEMORY
                        showMinigameSelection = false
                    }) {
                        Text("Memory Game")
                    }
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
        if(showFoodMenu){
            Dialog(onDismissRequest = {showFoodMenu = false}) {
                FoodMenuScreen(
                    onBack = { showFoodMenu = false },
                    modifier = modifier,
                    petStateViewModel = petStateViewModel,
                    lifecycleOwner = lifecycleOwner
                )
            }
        }

        if (isFeeding) {
            LaunchedEffect(Unit) {
                // TODO: changing it later with the animation or real logic
                delay(2000)
                isFeeding = false
            }
        }

        if (activePet.health <= 0f ||
                activePet.hunger <= 0f ||
                activePet.age >= Constants.PET_MATURITY_DEATH ||
                activePet.sickness == true && activePet.lastChecked - activePet.sicknessTimestamp > Constants.ONE_DAY * 2) {
            DeadPetDialog(
                petName = activePet.nickname,
                onStartNewLife = {
                    petStateViewModel.retirePetAndStartNew()
                }, false
            )
        }

        if (showRetireDialog) {
            DeadPetDialog(
                petName = activePet.nickname,
                onStartNewLife = {
                    petStateViewModel.retirePetAndStartNew()
                    showRetireDialog = false
                }, true
            )
        }

    }

}


@Preview(name = "MainScreenPreview")
@Composable
fun MainScreenPreview(){
    MainScreen(
        onNavigatePetHistory = {},
    )
}