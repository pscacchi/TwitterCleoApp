package ar.scacchipa.twittercloneapp.presentation.starter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ar.scacchipa.twittercloneapp.databinding.ActivityLoginLayoutBinding

class LoginActivity : AppCompatActivity() {

    private var binding: ActivityLoginLayoutBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginLayoutBinding.inflate(layoutInflater)
        setContentView(binding?.root)

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
