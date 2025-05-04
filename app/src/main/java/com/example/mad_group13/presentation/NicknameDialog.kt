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