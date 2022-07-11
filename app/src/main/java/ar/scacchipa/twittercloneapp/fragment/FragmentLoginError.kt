package ar.scacchipa.twittercloneapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ar.scacchipa.twittercloneapp.R
import ar.scacchipa.twittercloneapp.databinding.FragmentLoginErrorBinding

class FragmentLoginError : Fragment() {

    private var binding: FragmentLoginErrorBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginErrorBinding.inflate(inflater, container, false)

        binding?.okLoginErrorButton?.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentLoginError_to_fragmentLogin)
        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}