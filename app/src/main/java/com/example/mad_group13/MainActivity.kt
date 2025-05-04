package com.example.mad_group13

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mad_group13.navigation.Navigation
import com.example.mad_group13.ui.theme.MADGroup13Theme
import com.example.mad_group13.logic.PetActions
import com.example.mad_group13.presentation.NicknameDialog
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge() //TODO: figure out if we want this or not
        setContent {
            MADGroup13Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navigation(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

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
            Text(
                "Imagine there's a pet here.",
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

@Preview(name = "MainScreenPreview")
@Composable
fun MainScreenPreview(){
    MainScreen(
        onShowCurrentPetStats = {}
    )
}