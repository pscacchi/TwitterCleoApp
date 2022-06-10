package ar.scacchipa.twittercloneapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ar.scacchipa.twittercloneapp.ui.theme.TwitterCloneAppTheme

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        TwitterLogo()
    }
}

@Preview(showBackground = true)
@Composable
fun SpashPreview() {
    TwitterCloneAppTheme {
        SplashScreen()
    }
}
