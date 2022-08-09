package ar.scacchipa.twittercloneapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ar.scacchipa.twittercloneapp.R
import ar.scacchipa.twittercloneapp.databinding.FragmentMainBinding
import java.util.*

class FragmentMain : Fragment() {

    private val args: FragmentMainArgs by navArgs()
    private var binding: FragmentMainBinding? = null
    private val pagerStack = Stack<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity?.window?.let { window ->
            window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
            window.navigationBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        }
        binding = FragmentMainBinding.inflate(inflater)

        binding?.viewpager?.adapter = MainViewPagerAdapter(this)

        binding?.mainBottomNavigationView?.itemIconTintList = null
        binding?.mainBottomNavigationView?.itemActiveIndicatorColor = null
        binding?.mainBottomNavigationView?.setOnItemSelectedListener { menuItem ->
            val newPage = when (menuItem.itemId) {
                R.id.home_button -> 0
                R.id.search_button -> 1
                R.id.bell_button -> 2
                R.id.message_button -> 3
                else -> null
            }

            newPage?.let {
                binding?.viewpager?.currentItem = it
                pagerStack.push(it)
            }

            return@setOnItemSelectedListener true
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {
            if (pagerStack.empty()) {
                findNavController().navigateUp()
            } else {
                binding?.viewpager?.currentItem = pagerStack.pop()
            }
        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}

