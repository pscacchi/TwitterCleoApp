package ar.scacchipa.twittercloneapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ar.scacchipa.twittercloneapp.R
import ar.scacchipa.twittercloneapp.databinding.FragmentLoginLayoutBinding

class FragmentLogin : Fragment() {

    private var bindind: FragmentLoginLayoutBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindind = FragmentLoginLayoutBinding.inflate(inflater)
        bindind?.buttonLogin?.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentLogin_to_fragmentLoginApi)
        }
        return bindind?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindind = null
    }
}