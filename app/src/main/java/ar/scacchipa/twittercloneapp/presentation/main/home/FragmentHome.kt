package ar.scacchipa.twittercloneapp.presentation.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ar.scacchipa.twittercloneapp.databinding.FragmentHomeBinding
import ar.scacchipa.twittercloneapp.presentation.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentHome : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private var binding: FragmentHomeBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getTweets()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        binding?.tweetsRecyclerview?.apply {
            layoutManager = LinearLayoutManager(
                container?.context,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = TweetAdapter(listOf())
        }
        viewModel.tweets.observe(viewLifecycleOwner) { homeState ->
            val mainActivity = activity as MainActivity

            when (homeState) {
                is SuccessHomeState -> {
                    mainActivity.hideLoaderAnimation()
                    binding?.tweetsRecyclerview?.adapter = TweetAdapter(homeState.data)
                }
                is LoadingHomeState -> {
                    mainActivity.showLoaderAnimation()
                }
                is ErrorHomeState -> {
                    mainActivity.hideLoaderAnimation()
                    mainActivity.viewModel.onLogOut()
                }
            }
        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}