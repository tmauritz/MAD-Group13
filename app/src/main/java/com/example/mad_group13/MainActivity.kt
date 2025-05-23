package com.example.mad_group13

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mad_group13.model.AlarmScheduler
import com.example.mad_group13.navigation.Navigation
import com.example.mad_group13.ui.theme.MADGroup13Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge() //TODO: figure out if we want this or not

        AlarmScheduler.scheduleDailyTeslaCheck(this)

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
fun StartScreen(
    onShowMainScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text (
            "My Adorable Diamond!"
        )
        Button(onClick = onShowMainScreen) {
            Text (
                "Enter"
            )
        }
    }

}

@Preview(name = "MainScreenPreview")
@Composable
fun MainScreenPreview(){
    StartScreen(onShowMainScreen = {})
}