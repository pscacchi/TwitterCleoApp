package ar.scacchipa.twittercloneapp.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onPlaced
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    var username by mutableStateOf("")
    fun onShow(callback: ()->Unit) {
        CoroutineScope(Dispatchers.Unconfined).launch {
            delay(2000)
            launch(Dispatchers.Main) {
                Log.i("Login", "Launching callback")
                callback()
            }
        }
    }
}

@Composable
fun LoginScreen(
    callback: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    Box(
        modifier = Modifier.fillMaxSize().onPlaced { viewModel.onShow(callback) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Login Screen Mock",
            style = MaterialTheme.typography.titleLarge)
    }
}


