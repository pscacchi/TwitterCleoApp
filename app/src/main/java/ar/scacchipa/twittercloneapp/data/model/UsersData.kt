package ar.scacchipa.twittercloneapp.data.model

import com.google.gson.annotations.SerializedName

data class UsersData(
    @SerializedName("id") val id: String,
    @SerializedName("verified") val verified: Boolean,
    @SerializedName("name") val name: String,
    @SerializedName("username") val username:String,
    @SerializedName("profile_image_url") val profileImageUrl: String
)