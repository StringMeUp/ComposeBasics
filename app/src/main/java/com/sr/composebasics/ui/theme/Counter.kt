package com.sr.composebasics.ui.theme

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class Counter {

    @Preview
    @Composable
    fun Ui() {
        Column(
            modifier = Modifier.padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            val counter = remember { mutableStateOf(0) }
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Hey there, this is my result ${counter.value}.")

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(100.dp))

            AddButton(counter.value) {
                counter.value++
            }
        }
    }

    @Composable
    private fun AddButton(counter: Int, updateCounter: (Int) -> Unit) {
        Box(modifier = Modifier.size(100.dp)) {
            Button(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                shape = CircleShape,
                onClick = {
                    updateCounter(counter)
                    Log.d("Button tapped", "Ui: $counter")

                }) {
                Text(text = "Tap")
            }
        }
    }
}