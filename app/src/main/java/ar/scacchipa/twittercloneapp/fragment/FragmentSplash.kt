package ar.scacchipa.twittercloneapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ar.scacchipa.twittercloneapp.R
import ar.scacchipa.twittercloneapp.databinding.FragmentSplashLayoutBinding
import ar.scacchipa.twittercloneapp.viewmodel.SplashViewModel

class FragmentSplash: Fragment() {

    private var binding: FragmentSplashLayoutBinding? = null
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashLayoutBinding.inflate(inflater)
        splashViewModel.splashWasSpent.observe(viewLifecycleOwner) { splashWasSpent ->
            if (splashWasSpent) {
                findNavController().navigate(R.id.action_fragmentSplash_to_fragmentLogin)
            }
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        splashViewModel.spendSplash()
    }

    override fun onDestroy() {
        super.onDestroyView()
        binding = null
    }
}


