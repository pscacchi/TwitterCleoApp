package ar.scacchipa.twittercloneapp.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import ar.scacchipa.twittercloneapp.R
import ar.scacchipa.twittercloneapp.databinding.FragmentSplashLayoutBinding
import ar.scacchipa.twittercloneapp.presentation.MainActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentSplash: Fragment() {

    private var binding: FragmentSplashLayoutBinding? = null
    private val splashViewModel: SplashViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                splashViewModel.splashState.collect { state ->
                    when (state) {
                        SplashState.ShowLogo -> splashViewModel.spendSplash()
                        SplashState.GoToLogin -> goToLogin()
                        SplashState.SkipLogin -> skipLogin()
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashLayoutBinding.inflate(inflater)

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun goToLogin() {
        findNavController().navigate(R.id.action_fragmentSplash_to_fragmentLogin)
    }

    private fun skipLogin() {
        val intent = Intent(
            activity,
            MainActivity::class.java
        )

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)
    }
}


