package ar.scacchipa.twittercloneapp.domain.usecase

import ar.scacchipa.twittercloneapp.data.repository.ICredentialRepository
import ar.scacchipa.twittercloneapp.data.repository.ILoggedUserRepository
import ar.scacchipa.twittercloneapp.data.repository.ITweetRepository
import ar.scacchipa.twittercloneapp.domain.model.ResponseDomain
import ar.scacchipa.twittercloneapp.domain.model.TweetCardInfo
import ar.scacchipa.twittercloneapp.presentation.main.home.ErrorHomeState
import ar.scacchipa.twittercloneapp.presentation.main.home.State
import ar.scacchipa.twittercloneapp.presentation.main.home.SuccessHomeState
import ar.scacchipa.twittercloneapp.utils.Constants

class FetchFeedUseCase(
    private val credentialRepository: ICredentialRepository,
    private val loggedUserRepository: ILoggedUserRepository,
    private val tweetRepository: ITweetRepository
) {
    suspend operator fun invoke(): State<List<TweetCardInfo>> {
        return credentialRepository.recoverLocalCredential()?.accessToken?.let { accessToken ->
            loggedUserRepository.getLoggedUser()?.id?.let { id ->
                val responseDomain = tweetRepository.getTweets(
                    accessToken = accessToken,
                    loggedUserId = id
                )
                when (responseDomain) {
                    is ResponseDomain.Success<*> -> SuccessHomeState(
                        responseDomain.data as List<TweetCardInfo>
                    )
                    else -> ErrorHomeState(
                        error = Constants.TWEETS_DOWNLOAD_ERROR,
                        description = Constants.TWEETS_DOWNLOAD_ERROR_TXT
                    )
                }
            }
        } ?: ErrorHomeState(
            error = Constants.NO_LOGGED_USER_DATA_ERROR,
            description = Constants.NO_LOGGED_USER_DATA_ERROR_TXT
        )
    }
}
