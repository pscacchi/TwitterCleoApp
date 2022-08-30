package ar.scacchipa.twittercloneapp.data.model

data class UserAccessTokenDomain(
    val tokenType: String,
    val expiresIn: Int,
    val accessToken: String,
    val scope: String,
    val refreshToken: String
)