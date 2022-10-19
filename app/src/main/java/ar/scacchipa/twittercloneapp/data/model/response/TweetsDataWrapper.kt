package ar.scacchipa.twittercloneapp.data.model.response

import ar.scacchipa.twittercloneapp.data.model.IncludesTweetData
import ar.scacchipa.twittercloneapp.data.model.TweetData
import com.google.gson.annotations.SerializedName

data class TweetsDataWrapper(
    @SerializedName("data") val tweets: List<TweetData>,
    @SerializedName("includes") val includes: IncludesTweetData
)

