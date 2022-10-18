package ar.scacchipa.twittercloneapp.data

import ar.scacchipa.twittercloneapp.data.model.tweet.ReferenceTweetData
import ar.scacchipa.twittercloneapp.data.model.tweet.TweetsDataWrapper
import ar.scacchipa.twittercloneapp.data.model.tweet.UsersTweetData
import ar.scacchipa.twittercloneapp.domain.model.PublicMetricInfo
import ar.scacchipa.twittercloneapp.domain.model.ReferencedType
import ar.scacchipa.twittercloneapp.domain.model.TweetInfo

class TweetWrapperMapper : IMapper<TweetsDataWrapper, List<TweetInfo>> {
    override fun toDomain(value: TweetsDataWrapper): List<TweetInfo> {
        return value.tweets.map { tweetData ->
            val userData = value.includes.users.first { usersData ->
                usersData.id == tweetData.authorId
            }
            val referencedData: ReferenceTweetData? = tweetData.referencedTweets?.getOrNull(0)

            val referencedTweet = value.includes.referencedTweets.find { tweetData ->
                tweetData.id == referencedData?.referencedId
            }

            val referencedUserName = getRefUserName(
                referencedTweet?.authorId,
                value.includes.users)

            TweetInfo(
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

    override fun fromDomain(value: List<TweetInfo>): TweetsDataWrapper {
        TODO("Not yet implemented")
    }
    private fun getRefUserName(wantedId: String?, userList: List<UsersTweetData>) : String {
        return userList.find { userData ->
            userData.id == wantedId
        }?.name ?: ""
    }
}