package ar.scacchipa.twittercloneapp.presentation.main.home

import ar.scacchipa.twittercloneapp.domain.model.TweetCardInfo

typealias HomeState = State<List<TweetCardInfo>>
typealias SuccessHomeState = State.Success<List<TweetCardInfo>>
typealias LoadingHomeState = State.Loading<List<TweetCardInfo>>
typealias ErrorHomeState = State.Error<List<TweetCardInfo>>