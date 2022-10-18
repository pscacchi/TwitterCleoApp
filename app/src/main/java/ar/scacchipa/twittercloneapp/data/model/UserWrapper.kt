package ar.scacchipa.twittercloneapp.data.model

import com.google.gson.annotations.SerializedName

data class UserWrapper(
    @SerializedName("data") val data: UsersData
)