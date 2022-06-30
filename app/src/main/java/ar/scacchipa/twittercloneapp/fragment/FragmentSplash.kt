package ar.scacchipa.twittercloneapp.fragment

import android.graphics.Color
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
        splashViewModel.addObserver(viewLifecycleOwner) { splashWasSpent ->
            if (splashWasSpent) {
                findNavController().navigate(R.id.action_fragmentSplash_to_fragmentLogin)
            }
        }
        activity?.window?.let { window ->
            window.statusBarColor = Color.TRANSPARENT
            window.decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            )
            window.navigationBarColor =
                requireContext().resources.getColor(R.color.primary_background)
        }
        splashViewModel.spendSplash()
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroyView()
        binding = null
    }
}


