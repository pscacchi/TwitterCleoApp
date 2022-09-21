package ar.scacchipa.twittercloneapp.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ar.scacchipa.twittercloneapp.R
import ar.scacchipa.twittercloneapp.databinding.FragmentSplashLayoutBinding
import ar.scacchipa.twittercloneapp.presentation.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentSplash: Fragment() {

    private var binding: FragmentSplashLayoutBinding? = null
    private val splashViewModel: SplashViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashLayoutBinding.inflate(inflater)

        splashViewModel.mustLogin.observe(viewLifecycleOwner) { mustLogin ->
            when (mustLogin) {
                null -> splashViewModel.spendSplash()

                true -> {
                    findNavController().navigate(R.id.action_fragmentSplash_to_fragmentLogin)
                }

                false -> {
                    val intent = Intent(
                        activity,
                        MainActivity::class.java
                    )

                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK

                    startActivity(intent)
                }
            }
        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}


