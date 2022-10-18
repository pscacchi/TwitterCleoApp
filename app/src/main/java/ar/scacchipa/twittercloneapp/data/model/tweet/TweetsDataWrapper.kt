package ar.scacchipa.twittercloneapp.data.model.tweet

import com.google.gson.annotations.SerializedName

data class TweetsDataWrapper(
    @SerializedName("data") val tweets: List<TweetData>,
    @SerializedName("includes") val includes: IncludesTweetData
)

