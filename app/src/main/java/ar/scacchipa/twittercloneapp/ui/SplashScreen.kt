package ar.scacchipa.twittercloneapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ar.scacchipa.twittercloneapp.viewmodel.SplashViewModel

@Composable
fun SplashScreen(
    callback: ()->Unit,
    viewModel: SplashViewModel = SplashViewModel(rememberCoroutineScope(), callback)
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        TwitterLogo()
    }
    viewModel.spendSplash()
}
