package com.sr.composebasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sr.composebasics.ui.theme.Body
import com.sr.composebasics.ui.theme.ComposeBasicsTheme
import com.sr.composebasics.ui.theme.Counter
import com.sr.composebasics.ui.theme.Header
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeBasicsTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    /** Uncomment each one separately */
                    // counter state hoisting
                    // Counter().Ui()

                    // tip calculator with viewmodel
                    Column {
                        Header(viewModel = viewModel())
                        Body(viewModel = viewModel())
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeBasicsTheme {
//        Counter().Ui()
        Column {
            Header(viewModel = viewModel())
            Body(viewModel = viewModel())
        }
    }
}