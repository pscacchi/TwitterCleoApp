package ar.scacchipa.twittercloneapp.presentation.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.scacchipa.twittercloneapp.domain.model.ResponseDomain
import ar.scacchipa.twittercloneapp.domain.model.TweetInfo
import ar.scacchipa.twittercloneapp.domain.usecase.FetchFeedUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val fetchFeedUseCase: FetchFeedUseCase
): ViewModel() {

    private val _tweets = MutableLiveData<List<TweetInfo>>()
    val tweets: LiveData<List<TweetInfo>> = _tweets

    fun getTweets() {
        viewModelScope.launch {
            when (
                val respond = fetchFeedUseCase()
            ) {
                is ResponseDomain.Success<*> ->
                    _tweets.value = respond.data as ArrayList<TweetInfo>
                else -> println("No user")
            }
        }
    }
}