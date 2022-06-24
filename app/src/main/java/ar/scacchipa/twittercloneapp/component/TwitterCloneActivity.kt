package ar.scacchipa.twittercloneapp.component

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ar.scacchipa.twittercloneapp.databinding.ActivityMainBinding

class TwitterCloneActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}
