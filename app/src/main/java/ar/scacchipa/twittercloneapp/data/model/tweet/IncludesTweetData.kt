package ar.scacchipa.twittercloneapp.data.model.tweet

import com.google.gson.annotations.SerializedName

data class IncludesTweetData(
    @SerializedName("users") val users: List<UsersTweetData>,
    @SerializedName("tweets") val referencedTweets: List<TweetData>
)