package ar.scacchipa.twittercloneapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ar.scacchipa.twittercloneapp.databinding.FragmentLoginLayoutBinding

class FragmentLogin : Fragment() {

    var bindind: FragmentLoginLayoutBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindind = FragmentLoginLayoutBinding.inflate(inflater)
        return bindind?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindind = null
    }
}