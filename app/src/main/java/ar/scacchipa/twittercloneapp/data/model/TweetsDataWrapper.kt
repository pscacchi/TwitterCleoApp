package ar.scacchipa.twittercloneapp.data.model

import com.google.gson.annotations.SerializedName

data class TweetsDataWrapper(
    @SerializedName("data") val tweets: List<TweetData>,
    @SerializedName("includes") val includes: IncludesTweetData
)

