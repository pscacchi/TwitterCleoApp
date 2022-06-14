package ar.scacchipa.twittercloneapp.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onPlaced
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SpashViewModel: ViewModel() {
    fun onShow(callback: ()->Unit) {
        CoroutineScope(Dispatchers.Unconfined).launch {
            delay(2000)
            launch(Dispatchers.Main) {
                Log.i("Splash", "Launching callback")
                callback()
            }
        }
    }
}

@Composable
fun SplashScreen(
    callback: ()->Unit,
    viewModel: SpashViewModel = viewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .onPlaced {
                Log.i("Splash", "onPlaced")
                viewModel.onShow(callback)
                      },
        contentAlignment = Alignment.Center
    ) {
        TwitterLogo()
    }
}
