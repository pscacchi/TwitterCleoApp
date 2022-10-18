package ar.scacchipa.twittercloneapp.data.repository

import ar.scacchipa.twittercloneapp.data.IMapper
import ar.scacchipa.twittercloneapp.data.datasource.FileExternalSource
import ar.scacchipa.twittercloneapp.data.datasource.ITweetExternalSource
import ar.scacchipa.twittercloneapp.data.model.me.UserMeWrapper
import ar.scacchipa.twittercloneapp.data.model.tweet.TweetsDataWrapper
import ar.scacchipa.twittercloneapp.domain.model.ResponseDomain
import ar.scacchipa.twittercloneapp.domain.model.TweetInfo
import ar.scacchipa.twittercloneapp.domain.model.UserMeInfo

class TweetRepository(
    private val tweetExternalSource: ITweetExternalSource,
    private val fileExternalSource: FileExternalSource,
    private val credentialRepository: ICredentialRepository,
    private val userMeMapper: IMapper<UserMeWrapper, UserMeInfo>,
    private val tweetsMapper: IMapper<TweetsDataWrapper, List<TweetInfo>>
) : ITweetRepository {
    override suspend fun getUserMeInfo(): ResponseDomain {
        credentialRepository.recoverLocalCredential()?.let { credential ->
            println(credential.accessToken)
            val response = tweetExternalSource.getUserMeData("Bearer ${credential.accessToken}")
            if (response.isSuccessful) {
                response.body()?.let { userMeWrapper ->
                    return ResponseDomain.Success(
                        userMeMapper.toDomain(userMeWrapper)
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
                            tweetsMapper.toDomain(tweetsDataWrapper)
                        )
                    )
                }
            }
        }
        return ResponseDomain.Error()
    }

    private suspend fun addProfileImageByteArray(tweets: List<TweetInfo>): List<TweetInfo> {
        return tweets.map { tweetInfo ->
            tweetInfo.copy(
                profileBitmapByteArray = fileExternalSource.getFile(tweetInfo.profileImageUrl)
            )
        }
    }
}

