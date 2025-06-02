package com.example.mad_group13.presentation

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun NicknameDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var nickname by remember { mutableStateOf(TextFieldValue("")) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Give your pet a nickname") },
        text = {
            TextField(
                value = nickname,
                onValueChange = { nickname = it },
                label = { Text("Nickname") }
            )
        },
        confirmButton = {
            Button(onClick = { onConfirm(nickname.text) }) {
                Text("OK")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun DeadPetDialog(petName: String, onStartNewLife: () -> Unit, ifButtonCheck: Boolean) {
    var text: String = ""
    if (ifButtonCheck) {
        text = "You have now retired your pet $petName"
    } else {
        text = "$petName is dead"
    }
    androidx.compose.material3.AlertDialog(
        onDismissRequest = {},
        title = { Text(text) },
        text = { Text("Start a new life?") },
        confirmButton = {
            Button(onClick = { onStartNewLife() }) {
                Text("Start a new life")
            }
        }
    )
}
