package ar.scacchipa.twittercloneapp.component

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ar.scacchipa.twittercloneapp.ui.Splash
import ar.scacchipa.twittercloneapp.ui.theme.TwitterCloneAppTheme

class TwitterCloneActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TwitterCloneAppTheme {
                // A surface container using the 'background' color from the theme
                Splash()
            }
        }
    }
}
