package ar.scacchipa.twittercloneapp.component

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ar.scacchipa.twittercloneapp.ui.TwitterMainApp

class TwitterCloneActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TwitterMainApp()
        }
    }
}
