package ar.scacchipa.twittercloneapp.presentation.main.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.scacchipa.twittercloneapp.domain.usecase.FetchFeedUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val fetchFeedUseCase: FetchFeedUseCase
): ViewModel() {

    private val _homeState = MutableLiveData<HomeState>(
        State.Loading()
    )
    val tweets: MutableLiveData<HomeState> = _homeState

    fun getTweets() {
        viewModelScope.launch {
            _homeState.value = State.Loading()
            _homeState.value = fetchFeedUseCase() as HomeState
        }
    }
}
