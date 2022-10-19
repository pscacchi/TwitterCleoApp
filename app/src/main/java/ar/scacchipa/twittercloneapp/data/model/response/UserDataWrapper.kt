package ar.scacchipa.twittercloneapp.data.model.response

import ar.scacchipa.twittercloneapp.data.model.UserData
import com.google.gson.annotations.SerializedName

data class UserDataWrapper(
    @SerializedName("data") val data: UserData
)