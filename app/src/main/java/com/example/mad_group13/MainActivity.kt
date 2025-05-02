package com.example.mad_group13

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mad_group13.navigation.Navigation
import com.example.mad_group13.ui.theme.MADGroup13Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MADGroup13Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navigation()
                    innerPadding
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {

    Scaffold(
        /*topBar = {
            TopAppBar(
                title = { Text("My adorable Diamond!") }
            )

        }*/

    ) { innerPadding ->
        Box (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ){
            Text(
                ":D",
                modifier = Modifier.align(Alignment.Center)
            )
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row (
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
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
                        onClick = { print("Oh, please.") }
                    ) {
                        Text("Hmm")
                    }
                }

                Row (
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.fillMaxSize()
                ){
                    Button(
                        onClick = { print("I hath been clicked") }
                    ) {
                        Text("We")
                    }
                    Button(
                        onClick = { print("Shocking.") }
                    ) {
                        Text("are")
                    }
                    Button(
                        onClick = { print("Oh, please.") }
                    ) {
                        Text("more buttons")
                    }
                }
            }
        }


    }

}