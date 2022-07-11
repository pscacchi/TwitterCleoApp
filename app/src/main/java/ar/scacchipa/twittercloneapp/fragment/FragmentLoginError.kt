package ar.scacchipa.twittercloneapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ar.scacchipa.twittercloneapp.R
import ar.scacchipa.twittercloneapp.databinding.FragmentLoginErrorBinding
import ar.scacchipa.twittercloneapp.viewmodel.ErrorLoginViewModel

class FragmentLoginError : Fragment() {

    private var binding: FragmentLoginErrorBinding? = null
    private val viewModel: ErrorLoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginErrorBinding.inflate(inflater, container, false)

        viewModel.isReadyToGoToLoginFragLD.observe(viewLifecycleOwner) { isReady ->
            if (isReady) {
                findNavController().navigate(R.id.action_fragmentLoginError_to_fragmentLogin)
            }
        }
        binding?.okLoginErrorButton?.setOnClickListener {
            viewModel.checkIsReadyToGoToLoginFrag()
        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

