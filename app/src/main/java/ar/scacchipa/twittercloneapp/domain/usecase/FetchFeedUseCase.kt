package ar.scacchipa.twittercloneapp.domain.usecase

import ar.scacchipa.twittercloneapp.data.repository.ICredentialRepository
import ar.scacchipa.twittercloneapp.data.repository.ILoggedUserRepository
import ar.scacchipa.twittercloneapp.data.repository.ITweetRepository
import ar.scacchipa.twittercloneapp.domain.model.ResponseDomain

class FetchFeedUseCase(
    private val credentialRepository: ICredentialRepository,
    private val loggedUserRepository: ILoggedUserRepository,
    private val tweetRepository: ITweetRepository
) {
    suspend operator fun invoke(): ResponseDomain {
        credentialRepository.recoverLocalCredential()?.accessToken?.let { accessToken ->
            loggedUserRepository.getLoggedUser()?.id?.let { id ->
                return tweetRepository.getTweets(
                    accessToken = accessToken,
                    loggedUserId = id
                )
            }
        }
        return ResponseDomain.Error()
    }
}
