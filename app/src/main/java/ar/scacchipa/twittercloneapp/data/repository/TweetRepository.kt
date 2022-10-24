package ar.scacchipa.twittercloneapp.data.repository

import ar.scacchipa.twittercloneapp.data.IMapper
import ar.scacchipa.twittercloneapp.data.datasource.ITweetExternalSource
import ar.scacchipa.twittercloneapp.data.model.response.TweetsDataWrapper
import ar.scacchipa.twittercloneapp.domain.model.ResponseDomain
import ar.scacchipa.twittercloneapp.domain.model.TweetCardInfo
import ar.scacchipa.twittercloneapp.utils.Constants

class TweetRepository(
    private val tweetExternalSource: ITweetExternalSource,
    private val credentialRepository: ICredentialRepository,
    private val loggedUserRepository: ILoggedUserRepository,
    private val tweetsWrapperMapper: IMapper<TweetsDataWrapper, List<TweetCardInfo>>
) : ITweetRepository {
    override suspend fun getTweets(): ResponseDomain {
        val loggedUserData = loggedUserRepository.getLoggedUser()
            ?: return ResponseDomain.Error(
                error = Constants.NO_LOGGED_USER_DATA_ERROR,
                errorDescription = Constants.NO_LOGGED_USER_DATA_ERROR_TXT
            )
        credentialRepository.recoverLocalCredential()?.let { credential ->
            val tweetsRespond = tweetExternalSource.getTweets(
                userId = loggedUserData.id,
                bearerCode = "Bearer ${credential.accessToken}"
            )

            if (tweetsRespond.isSuccessful) {
                tweetsRespond.body()?.let { tweetsDataWrapper ->
                    return ResponseDomain.Success(
                        tweetsWrapperMapper.toDomain(tweetsDataWrapper)
                    )
                }
            }
        }
        return ResponseDomain.Error(
            error = Constants.TWEETS_DOWNLOAD_ERROR,
            errorDescription = Constants.TWEETS_DOWNLOAD_ERROR_TXT
        )
    }
}

