package ar.scacchipa.twittercloneapp.data.repository

import ar.scacchipa.twittercloneapp.data.IMapper
import ar.scacchipa.twittercloneapp.data.datasource.FileExternalSource
import ar.scacchipa.twittercloneapp.data.datasource.ITweetExternalSource
import ar.scacchipa.twittercloneapp.data.model.response.TweetsDataWrapper
import ar.scacchipa.twittercloneapp.data.model.response.UserDataWrapper
import ar.scacchipa.twittercloneapp.domain.model.ResponseDomain
import ar.scacchipa.twittercloneapp.domain.model.TweetCardInfo
import ar.scacchipa.twittercloneapp.domain.model.UserInfo

class TweetRepository(
    private val tweetExternalSource: ITweetExternalSource,
    private val fileExternalSource: FileExternalSource,
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
        return ResponseDomain.Error()
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
                        addProfileImageByteArray(
                            tweetsWrapperMapper.toDomain(tweetsDataWrapper)
                        )
                    )
                }
            }
        }
        return ResponseDomain.Error()
    }

    private suspend fun addProfileImageByteArray(tweets: List<TweetCardInfo>): List<TweetCardInfo> {
        return tweets.map { tweetInfo ->
            tweetInfo.copy(
                profileBitmapByteArray = fileExternalSource.getFile(tweetInfo.user.profileImageUrl)
            )
        }
    }
}

