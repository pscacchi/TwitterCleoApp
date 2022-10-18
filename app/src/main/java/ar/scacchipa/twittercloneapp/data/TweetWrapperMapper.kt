package ar.scacchipa.twittercloneapp.data

import ar.scacchipa.twittercloneapp.data.model.IncludesTweetData
import ar.scacchipa.twittercloneapp.data.model.PublicMetricData
import ar.scacchipa.twittercloneapp.data.model.ReferenceTweetData
import ar.scacchipa.twittercloneapp.data.model.TweetData
import ar.scacchipa.twittercloneapp.data.model.TweetsDataWrapper
import ar.scacchipa.twittercloneapp.data.model.UsersData
import ar.scacchipa.twittercloneapp.domain.model.PublicMetricInfo
import ar.scacchipa.twittercloneapp.domain.model.ReferencedType
import ar.scacchipa.twittercloneapp.domain.model.TweetCardInfo

class TweetWrapperMapper : IMapper<TweetsDataWrapper, List<TweetCardInfo>> {
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
                value.includes.users)

            TweetCardInfo(
                text = tweetData.text,
                publicMetrics = PublicMetricInfo(
                    retweetCount = tweetData.publicMetricData.retweetCount
                            + tweetData.publicMetricData.quoteCount,
                    replyCount = tweetData.publicMetricData.replyCount,
                    likeCount = tweetData.publicMetricData.likeCount
                ),
                authorId = tweetData.authorId,
                verified = userData.verified,
                name = userData.name,
                username = userData.username,
                referenceTweet = when(referencedData?.type) {
                    "retweeted" -> ReferencedType.RetweetedType(referencedUserName)
                    "replied_to" -> ReferencedType.RepliedToType(referencedUserName)
                    "quoted" -> ReferencedType.QuotedType(referencedUserName)
                    else -> ReferencedType.NoReferencedType
                },
                profileImageUrl = userData.profileImageUrl
            )
        }
    }

    override fun fromDomain(value: List<TweetCardInfo>): TweetsDataWrapper {
        return TweetsDataWrapper(
            tweets = value.map { tweetCardInfo ->
                TweetData(
                    id = "",
                    authorId = tweetCardInfo.authorId,
                    text = tweetCardInfo.authorId,
                    referencedTweets = listOf(
                        ReferenceTweetData(
                        type = "",
                        referencedId = ""
                    )
                    ),
                    publicMetricData = PublicMetricData(
                        retweetCount = tweetCardInfo.publicMetrics.retweetCount,
                        replyCount =  tweetCardInfo.publicMetrics.replyCount,
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
    private fun getRefUserName(wantedId: String?, userList: List<UsersData>) : String {
        return userList.find { userData ->
            userData.id == wantedId
        }?.name ?: ""
    }
}