package ar.scacchipa.twittercloneapp.data.model

import com.google.gson.annotations.SerializedName

class PublicMetricData(
    @SerializedName("retweet_count") val retweetCount: Int,
    @SerializedName("reply_count") val replyCount: Int,
    @SerializedName("like_count") val likeCount: Int,
    @SerializedName("quote_count") val quoteCount: Int
)