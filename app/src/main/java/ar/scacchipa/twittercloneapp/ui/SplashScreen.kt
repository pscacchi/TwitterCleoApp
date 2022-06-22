package ar.scacchipa.twittercloneapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.scacchipa.twittercloneapp.viewmodel.SplashViewModel

@Composable
fun SplashScreen(
    callback: ()->Unit,
    viewModel: SplashViewModel = viewModel()
) {
// Option 1
    viewModel.composeScope = rememberCoroutineScope()
    viewModel.onFinishTime = callback
// Option 2
//    LaunchedEffect(key1 = true) {
//        delay(5000)
//        callback()
//    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        TwitterLogo()
    }
}
