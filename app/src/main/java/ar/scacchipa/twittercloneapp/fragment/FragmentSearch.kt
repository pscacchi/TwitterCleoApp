package ar.scacchipa.twittercloneapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ar.scacchipa.twittercloneapp.R
import ar.scacchipa.twittercloneapp.databinding.FragmentSearchBinding

class FragmentSearch : Fragment() {

    private var binding: FragmentSearchBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity?.window?.let { window ->
            window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
            window.navigationBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        }
        binding = FragmentSearchBinding.inflate(inflater)

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}