package com.example.mad_group13.presentation

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import com.example.mad_group13.R

@Composable
fun NicknameDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var nickname by remember { mutableStateOf(TextFieldValue("")) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.nickname_dialog_title)) },
        text = {
            TextField(
                value = nickname,
                onValueChange = { nickname = it },
                label = { Text(stringResource(R.string.nickname_field_label)) }
            )
        },
        confirmButton = {
            Button(onClick = { onConfirm(nickname.text) }) {
                Text(stringResource(R.string.button_ok))
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(stringResource(R.string.button_cancel))
            }
        }
    )
}

@Composable
fun DeadPetDialog(petName: String, onStartNewLife: () -> Unit, ifButtonCheck: Boolean) {
    val context = LocalContext.current
    var text: String = ""
    if (ifButtonCheck) {
        text = context.getString(R.string.dead_pet_retired, petName)
    } else {
        text = context.getString(R.string.dead_pet_dead, petName)
    }
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text) },
        text = { stringResource(R.string.dead_pet_start_new) },
        confirmButton = {
            Button(onClick = { onStartNewLife() }) {
                Text(stringResource(R.string.button_start_new_life))
            }
        }
    )
}

@Composable
fun SicknessDialog(sick: Boolean, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(stringResource(if (sick) R.string.sick_true_title else R.string.sick_false_title)) },
        text = { Text(stringResource(if (sick) R.string.sick_true_text else R.string.sick_false_text)) },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text(stringResource(R.string.button_ok))
            }
        }
    )
}