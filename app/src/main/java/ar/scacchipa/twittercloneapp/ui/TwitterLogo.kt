package ar.scacchipa.twittercloneapp.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import ar.scacchipa.twittercloneapp.R
import ar.scacchipa.twittercloneapp.ui.theme.TwitterCloneAppTheme

@Composable
@Preview(showBackground = true)
fun TwitterLogo() {
    Image(
        imageVector = ImageVector.vectorResource(id = R.drawable.twitter_logo),
        contentDescription = "Twitter Logo")
}

@Composable
@Preview(showBackground = true)
fun LoginPreview() {
    TwitterCloneAppTheme {
        TwitterLogo()
    }
}