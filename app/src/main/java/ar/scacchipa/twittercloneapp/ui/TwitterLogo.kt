package ar.scacchipa.twittercloneapp.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import ar.scacchipa.twittercloneapp.R
import ar.scacchipa.twittercloneapp.ui.theme.TwitterCloneAppTheme

@Composable
fun TwitterLogo() {
    Image(
        imageVector = ImageVector.vectorResource(id = R.drawable.twitter_logo),
        contentDescription = "Twitter Logo")
}

@Preview(showBackground = true)
@Composable
fun TwitterLogoPreview() {
    TwitterCloneAppTheme {
        TwitterLogo()
    }
}