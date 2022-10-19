package ar.scacchipa.twittercloneapp.domain.usecase

import ar.scacchipa.twittercloneapp.data.repository.ITweetRepository
import ar.scacchipa.twittercloneapp.domain.model.ResponseDomain
import ar.scacchipa.twittercloneapp.domain.model.UserInfo
import ar.scacchipa.twittercloneapp.utils.Constants

class FetchFeedUseCase(
    private val tweetRepository: ITweetRepository
) {
    suspend operator fun invoke(): ResponseDomain {
        return when (
            val response = tweetRepository.getUserMeInfo()
        ) {
            is ResponseDomain.Success<*> -> response.data as UserInfo
            else -> null
        }?.let { userMeInfo ->
            return tweetRepository.getTweets(
                userId = userMeInfo.id
            )
        } ?: ResponseDomain.Error(
            Constants.ACCESS_TOKEN_ERROR,
            Constants.ACCESS_TOKEN_ERROR_TXT
        )
    }
}
