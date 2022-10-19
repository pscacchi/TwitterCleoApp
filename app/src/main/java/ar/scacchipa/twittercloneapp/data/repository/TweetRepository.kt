package ar.scacchipa.twittercloneapp.data.repository

import ar.scacchipa.twittercloneapp.data.IMapper
import ar.scacchipa.twittercloneapp.data.datasource.ITweetExternalSource
import ar.scacchipa.twittercloneapp.data.model.response.TweetsDataWrapper
import ar.scacchipa.twittercloneapp.data.model.response.UserDataWrapper
import ar.scacchipa.twittercloneapp.domain.model.ResponseDomain
import ar.scacchipa.twittercloneapp.domain.model.TweetCardInfo
import ar.scacchipa.twittercloneapp.domain.model.UserInfo
import ar.scacchipa.twittercloneapp.utils.Constants

class TweetRepository(
    private val tweetExternalSource: ITweetExternalSource,
    private val credentialRepository: ICredentialRepository,
    private val userWrapperMapper: IMapper<UserDataWrapper, UserInfo>,
    private val tweetsWrapperMapper: IMapper<TweetsDataWrapper, List<TweetCardInfo>>
) : ITweetRepository {
    override suspend fun getUserMeInfo(): ResponseDomain {
        credentialRepository.recoverLocalCredential()?.let { credential ->
            val response = tweetExternalSource.getUserMeData("Bearer ${credential.accessToken}")
            if (response.isSuccessful) {
                response.body()?.let { userMeWrapper ->
                    return ResponseDomain.Success(
                        userWrapperMapper.toDomain(userMeWrapper)
                    )
                }
            }
        }
        return ResponseDomain.Error(
            error = Constants.USER_DOWNLOAD_ERROR,
            errorDescription = Constants.USER_DOWNLOAD_ERROR_TXT)
    }

    override suspend fun getTweets(userId: String) : ResponseDomain {
        credentialRepository.recoverLocalCredential()?.let { credential ->
            val tweetsRespond = tweetExternalSource.getTweets(
                userId = userId,
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
            errorDescription = Constants.ACCESS_TOKEN_ERROR_TXT)
    }
}

