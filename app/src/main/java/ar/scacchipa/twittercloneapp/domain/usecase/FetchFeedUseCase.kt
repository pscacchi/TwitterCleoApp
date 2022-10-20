package ar.scacchipa.twittercloneapp.domain.usecase

import ar.scacchipa.twittercloneapp.data.repository.ITweetRepository
import ar.scacchipa.twittercloneapp.domain.model.ResponseDomain

class FetchFeedUseCase(
    private val tweetRepository: ITweetRepository
) {
    suspend operator fun invoke(): ResponseDomain {
        return tweetRepository.getTweets()
    }
}
