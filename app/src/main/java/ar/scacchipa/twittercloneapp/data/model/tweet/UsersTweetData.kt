package ar.scacchipa.twittercloneapp.data.model.tweet

import com.google.gson.annotations.SerializedName

data class UsersTweetData(
    @SerializedName("id") val id: String,
    @SerializedName("verified") val verified: Boolean,
    @SerializedName("name") val name: String,
    @SerializedName("username") val username:String,
    @SerializedName("profile_image_url") val profileImageUrl: String
)