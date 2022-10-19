package ar.scacchipa.twittercloneapp.data

import ar.scacchipa.twittercloneapp.data.model.IncludesTweetData
import ar.scacchipa.twittercloneapp.data.model.PublicMetricData
import ar.scacchipa.twittercloneapp.data.model.ReferenceTweetData
import ar.scacchipa.twittercloneapp.data.model.TweetData
import ar.scacchipa.twittercloneapp.data.model.UserData
import ar.scacchipa.twittercloneapp.data.model.response.TweetsDataWrapper
import ar.scacchipa.twittercloneapp.domain.model.PublicMetricInfo
import ar.scacchipa.twittercloneapp.domain.model.ReferencedType
import ar.scacchipa.twittercloneapp.domain.model.TweetCardInfo
import ar.scacchipa.twittercloneapp.domain.model.UserInfo

class TweetWrapperMapper(
    private val userMapper: IMapper<UserData, UserInfo>
) : IMapper<TweetsDataWrapper, List<TweetCardInfo>> {
    override fun toDomain(value: TweetsDataWrapper): List<TweetCardInfo> {
        return value.tweets.map { tweetData ->
            val userData = value.includes.users.first { usersData ->
                usersData.id == tweetData.authorId
            }
            val referencedData: ReferenceTweetData? = tweetData.referencedTweets?.getOrNull(0)

            val referencedTweet = value.includes.referencedTweets.find { tweet ->
                tweet.id == referencedData?.referencedId
            }

            val referencedUserName = getRefUserName(
                referencedTweet?.authorId,
                value.includes.users
            )

            TweetCardInfo(
                text = tweetData.text,
                user = userMapper.toDomain(userData),
                publicMetrics = PublicMetricInfo(
                    retweetCount = tweetData.publicMetricData.retweetCount
                            + tweetData.publicMetricData.quoteCount,
                    replyCount = tweetData.publicMetricData.replyCount,
                    likeCount = tweetData.publicMetricData.likeCount
                ),
                referenceTweet = when (referencedData?.type) {
                    "retweeted" -> ReferencedType.RetweetedType(referencedUserName)
                    "replied_to" -> ReferencedType.RepliedToType(referencedUserName)
                    "quoted" -> ReferencedType.QuotedType(referencedUserName)
                    else -> ReferencedType.NoReferencedType
                }
            )
        }
    }

    override fun fromDomain(value: List<TweetCardInfo>): TweetsDataWrapper {
        return TweetsDataWrapper(
            tweets = value.map { tweetCardInfo ->
                TweetData(
                    id = "",
                    authorId = tweetCardInfo.user.id,
                    text = tweetCardInfo.user.id,
                    referencedTweets = listOf(
                        ReferenceTweetData(
                            type = "",
                            referencedId = ""
                        )
                    ),
                    publicMetricData = PublicMetricData(
                        retweetCount = tweetCardInfo.publicMetrics.retweetCount,
                        replyCount = tweetCardInfo.publicMetrics.replyCount,
                        likeCount = tweetCardInfo.publicMetrics.likeCount,
                        quoteCount = 0
                    )
                )
            },
            includes = IncludesTweetData(
                users = listOf(),
                referencedTweets = listOf()
            )
        )
    }
    private fun getRefUserName(wantedId: String?, userList: List<UserData>) : String {
        return userList.find { userData ->
            userData.id == wantedId
        }?.name ?: ""
    }
}