package ar.scacchipa.twittercloneapp.data.model

import com.google.gson.annotations.SerializedName

data class IncludesTweetData(
    @SerializedName("users") val users: List<UserData>,
    @SerializedName("tweets") val referencedTweets: List<TweetData>
)