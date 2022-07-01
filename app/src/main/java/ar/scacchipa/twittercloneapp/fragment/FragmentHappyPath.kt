package ar.scacchipa.twittercloneapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import ar.scacchipa.twittercloneapp.databinding.FragmentHappyPathBinding

class FragmentHappyPath : Fragment() {

    val args: FragmentHappyPathArgs by navArgs()
    var binding: FragmentHappyPathBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHappyPathBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.bearerCode?.text = args.bearerToken
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}