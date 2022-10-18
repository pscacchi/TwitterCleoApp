package ar.scacchipa.twittercloneapp.presentation.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ar.scacchipa.twittercloneapp.databinding.FragmentHomeBinding
import ar.scacchipa.twittercloneapp.domain.model.ResponseDomain
import ar.scacchipa.twittercloneapp.domain.model.TweetCardInfo
import ar.scacchipa.twittercloneapp.presentation.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentHome : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private var binding: FragmentHomeBinding? = null

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
        viewModel.tweets.observe(viewLifecycleOwner) { tweets ->
            when(tweets) {
                is ResponseDomain.Success<*> ->
                    binding?.tweetsRecyclerview?.adapter = TweetAdapter(tweets.data as List<TweetCardInfo>)
                else ->
                    (activity as MainActivity).viewModel.onLogOut()
            }
        }

        viewModel.getTweets()

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}