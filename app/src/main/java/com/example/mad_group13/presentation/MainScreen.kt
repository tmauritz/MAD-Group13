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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.mad_group13.R
import com.example.mad_group13.logic.PetActions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onShowCurrentPetStats: () -> Unit,
    modifier: Modifier = Modifier
) {

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
            Image(
                painter = painterResource(id = R.drawable.dia_1_purple),
                contentDescription = "A pretty diamond!",
                modifier = modifier.align(Alignment.Center)
            )
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
                        onClick = { print("I hath been clicked") }
                    ) {
                        Text("Button!")
                    }
                    Button(
                        onClick = { print("Shocking.") }
                    ) {
                        Text("Button!")
                    }
                    Button(
                        onClick = {
                            showNicknameDialog = true
                        }
                    ) {
                        Text("Nickname")
                    }
                }

                Row (
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom,
                    modifier = modifier.fillMaxSize()
                ){
                    Button(
                        onClick = { print("I hath been clicked") }
                    ) {
                        Text("Moar")
                    }
                    Button(
                        onClick = {
                            // TODO: later implement state-management
                            PetActions.feedPet() }
                    ) {
                        Text("Feed")
                    }
                    Button(
                        onClick = onShowCurrentPetStats
                    ) {
                        Text("Stats")
                    }
                }
            }
            if (showNicknameDialog) {
                NicknameDialog(
                    onDismiss = { showNicknameDialog = false },
                    onConfirm = { newName ->
                        PetActions.changePetNickname(newName)
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
