package ar.scacchipa.twittercloneapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ar.scacchipa.twittercloneapp.databinding.FragmentSplashLayoutBinding

class FragmentSplash: Fragment() {

    private var binding: FragmentSplashLayoutBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashLayoutBinding.inflate(inflater)
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroyView()
        binding = null
    }
}


