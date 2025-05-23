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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.mad_group13.R
import com.example.mad_group13.presentation.viewModel.PetStateViewModel
import com.example.mad_group13.presentation.viewModel.StockViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigatePetHistory: () -> Unit,
    modifier: Modifier = Modifier,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    petStateViewModel: PetStateViewModel = hiltViewModel(),
    stockViewModel: StockViewModel = hiltViewModel()
) {

    val activePet by petStateViewModel.petState.collectAsState()
    val teslaPrice by stockViewModel.teslaPrice.collectAsState()


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
                title = { Text(stringResource(R.string.app_name)) },
                actions = {
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Text(text = "TSLA: $teslaPrice")
                        Button(
                            onClick = { stockViewModel.fetchTeslaStock() },
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            Text("GET STOCKS")
                        }
                    }
                }
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
                        Text("Heal(DBG)")
                    }
                    Button(
                        onClick = { petStateViewModel.retirePetAndStartNew() }
                    ) {
                        Text("Retire(DBG)(final)!")
                    }
                    Button(
                        onClick = { petStateViewModel.updatePetState(waitForTimeInterval = false) }
                    ) {
                        Text("Pass Time(DBG)")
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
                    StatDisplay(stringResource(R.string.label_stat_health), activePet.health)
                    StatDisplay(stringResource(R.string.label_stat_hunger), activePet.hunger)
                    StatDisplay(stringResource(R.string.label_stat_happiness), activePet.happiness)
                    StatDisplay(stringResource(R.string.label_stat_activity), activePet.activity)
                }

                Row (
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom,
                    modifier = modifier.fillMaxSize()
                ){
                    Button(
                        onClick = {
                            showNicknameDialog = true
                        }
                    ) {
                        Text(stringResource(R.string.button_nickname))
                    }
                    // TODO: Auskommentiert, wegen Fehlermeldung bei mir (Chris)
                    /*Button(
                        onClick = {
                            petStateViewModel.feedPet() }
                    ) {
                        Text(stringResource(R.string.button_feed))
                    }*/
                    Button(
                        onClick = onNavigatePetHistory
                    ) {
                        Text(stringResource(R.string.button_history))
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