package ar.scacchipa.twittercloneapp.component

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ar.scacchipa.twittercloneapp.databinding.ActivityMainLayoutBinding

class TwitterCloneActivity : AppCompatActivity() {

    private var binding: ActivityMainLayoutBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainLayoutBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
