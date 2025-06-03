package com.example.mad_group13.presentation.minigame

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mad_group13.R

@Composable
fun MemoryGame(onWin: ()-> Unit) {
    val cardList = remember { mutableStateListOf(1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6).also {it.shuffle()} }
    val selectedCards = remember { mutableStateListOf<Int>() }
    val finishedMatches = remember { mutableStateListOf<Int>() }
    var count = 0

    Card() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = stringResource(R.string.minigame_memory_title),
                modifier = Modifier.padding(bottom = 20.dp)
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(
                    count = cardList.size
                ) { index ->
                    val isSelected = index in selectedCards
                    val isFinished = index in finishedMatches

                    MemoryCard(
                        cardList[index],
                        isSelected = isSelected,
                        isFinished = isFinished,
                        onSelect = {
                            if (selectedCards.size == 2) selectedCards.clear()
                            if (isSelected) {
                                selectedCards.remove(index)
                            } else if (selectedCards.size < 2) {
                                selectedCards.add(index)
                                if (selectedCards.size == 2 && cardList[selectedCards[0]] == cardList[selectedCards[1]]) {
                                    finishedMatches.addAll(selectedCards)
                                    selectedCards.clear()
                                }
                            }}
                    )
                }
            }
            if (finishedMatches.size == cardList.size) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(stringResource(R.string.minigame_memory_win))
                    Button(
                        onClick = onWin
                    ) { Text("Yay!") }
                }
            }
        }
    }
}

@Composable
fun MemoryCard(
    cardType: Int,
    isSelected: Boolean,
    isFinished: Boolean,
    onSelect: () -> Unit
) {
    Card (
        modifier = Modifier
            .padding(5.dp)
            .clickable {
                onSelect()
                Log.i("MAD_MemoryGame", "Card has been clicked")
            },
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = getDrawable(if (isSelected || isFinished) cardType else 0)),
                contentDescription = "I'm a card!",
                modifier = Modifier
                    .width(85.dp)

            )
        }
    }
}

private fun getDrawable(cardType: Int): Int {
    return when(cardType) {
        0 -> R.drawable.memory_back
        1 -> R.drawable.memory_1
        2 -> R.drawable.memory_2
        3 -> R.drawable.memory_3
        4 -> R.drawable.memory_4
        5 -> R.drawable.memory_5
        6 -> R.drawable.memory_6
        else -> R.drawable.memory_back
    }
}

@Preview(name="MemoryPreview")
@Composable
fun MemoryGamePreview(){
    MemoryGame({})
}