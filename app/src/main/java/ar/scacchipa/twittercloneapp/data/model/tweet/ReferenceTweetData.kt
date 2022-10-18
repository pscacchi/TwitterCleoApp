package ar.scacchipa.twittercloneapp.data.model.tweet

import com.google.gson.annotations.SerializedName

data class ReferenceTweetData(
    @SerializedName("type") val type: String,
    @SerializedName("id") val referencedId: String
)