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
        Text ("My Adorable Diamond!")
        Text (
            text = "Did you know...\n" + getDailyMessage(),
            modifier = modifier.padding(top = 16.dp)
        )
        Button(
            onClick = onShowMainScreen,
            modifier = modifier.padding(top = 16.dp)
        ) {
            Text (
                "Ok"
            )
        }
    }

}

fun getDailyMessage(): String {
    val messageList = listOf<String>(
        "Fun Fact: Diamonds are forever. So is your pet’s emotional damage if you forget to feed it.",
        "Daily Wisdom: Your diamond has more mood swings than your group chat. Respect that.",
        "Reminder: You technically agreed to care for this digital diva. There’s no escape clause.",
        "Your adorable diamond has 17 different ways to guilt-trip you. You’ve unlocked 6.",
        "Self-care tip: Drink water. Sleep well. Don’t let your virtual gem spiral into chaos again.",
        "Science says diamonds form under pressure. Yours forms drama under slight inconvenience.",
        "Fun Fact: Ignoring your diamond is legally classified as emotional neglect in 37 imaginary countries.",
        "Quote of the Day: ‘Why be normal when you can be sparkly and passive-aggressive?’ – Your Pet",
        "Pro Tip: If your diamond starts blinking aggressively... run. Or feed it. Or both.",
        "Every time you ignore your diamond, a glittery tantrum gains power."
    )
    return messageList.random()
}

@Preview(name = "MainScreenPreview")
@Composable
fun MainScreenPreview(){
    StartScreen(onShowMainScreen = {})
}