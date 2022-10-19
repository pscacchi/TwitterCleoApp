package ar.scacchipa.twittercloneapp.domain.model

data class UserInfo(
    val id: String,
    val verified: Boolean,
    val name: String,
    val username:String,
    val profileImageUrl: String
)