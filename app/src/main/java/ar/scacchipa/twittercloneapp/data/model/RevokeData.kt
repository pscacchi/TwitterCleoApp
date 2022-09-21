package ar.scacchipa.twittercloneapp.data.model

import com.google.gson.annotations.SerializedName

data class RevokeData(
    @SerializedName("revoked") val revoked: Boolean
)