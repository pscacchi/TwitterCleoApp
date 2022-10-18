package ar.scacchipa.twittercloneapp.data.model

import com.google.gson.annotations.SerializedName

data class TweetData(
    @SerializedName("id") val id: String,
    @SerializedName("author_id") val authorId: String,
    @SerializedName("text") val text: String,
    @SerializedName("referenced_tweets") val referencedTweets: List<ReferenceTweetData>?,
    @SerializedName("public_metrics")val publicMetricData: PublicMetricData
)

