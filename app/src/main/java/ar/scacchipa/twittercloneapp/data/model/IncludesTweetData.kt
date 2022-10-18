package ar.scacchipa.twittercloneapp.data.model

import com.google.gson.annotations.SerializedName

data class IncludesTweetData(
    @SerializedName("users") val users: List<UsersData>,
    @SerializedName("tweets") val referencedTweets: List<TweetData>
)