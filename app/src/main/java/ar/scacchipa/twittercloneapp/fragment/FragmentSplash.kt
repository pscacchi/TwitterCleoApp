package ar.scacchipa.twittercloneapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ar.scacchipa.twittercloneapp.R
import ar.scacchipa.twittercloneapp.component.MainCloneActivity
import ar.scacchipa.twittercloneapp.databinding.FragmentSplashLayoutBinding
import ar.scacchipa.twittercloneapp.viewmodel.SplashViewModel
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

        splashViewModel.splashWasSpent.observe(viewLifecycleOwner) { splashWasSpent ->
            if (splashWasSpent) {
                if (splashViewModel.recoverUserAccessToken().accessToken.isEmpty()) {
                    findNavController().navigate(R.id.action_fragmentSplash_to_fragmentLogin)
                } else {
                    startActivity(
                        Intent(
                            activity,
                            MainCloneActivity::class.java
                        )
                    )
                }
            }
        }
        splashViewModel.spendSplash()
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}


