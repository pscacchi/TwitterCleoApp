package ar.scacchipa.twittercloneapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import ar.scacchipa.twittercloneapp.databinding.FragmentHomeBinding

class FragmentHome : Fragment() {

    private val args: FragmentHomeArgs by navArgs()
    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        binding?.homeBottomNavigationView?.itemIconTintList = null
        binding?.homeBottomNavigationView?.itemActiveIndicatorColor = null
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}