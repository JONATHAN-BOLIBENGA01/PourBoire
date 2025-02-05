package com.example.pourboire

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pourboire.ui.theme.PourBoireTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PourBoireTheme {
                CaesarEnodeur()
            }
        }
    }
}

fun caesarCipher(text: String, shift: Int): String {
    return text.map {
        when {
            it.isLetter() -> {
                val base = if (it.isUpperCase()) 'A' else 'a'
                (((it - base + shift) % 26) + base.code).toChar()
            }
            else -> it
        }
    }.joinToString("")
}

fun caesarDecipher(text: String, shift: Int): String {
    return caesarCipher(text, 26 - (shift % 26))
}

@Composable
fun CaesarEnodeur() {
    var inputText by remember { mutableStateOf("") }
    var shiftValue by remember { mutableStateOf("") }
    val shift = shiftValue.toIntOrNull() ?: 0
    val encodedText = caesarCipher(inputText, shift)
    val decodedText = caesarDecipher(encodedText, shift)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("Texte à chiffrer") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = shiftValue,
            onValueChange = { shiftValue = it },
            label = { Text("Décalage") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Texte chiffré : $encodedText")
        Text("Texte déchiffré : $decodedText")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCaesarCipherScreen() {
    PourBoireTheme {
        CaesarEnodeur()
    }
}
