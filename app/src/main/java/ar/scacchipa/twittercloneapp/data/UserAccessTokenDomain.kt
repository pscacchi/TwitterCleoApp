package ar.scacchipa.twittercloneapp.data

data class UserAccessTokenDomain(
    val tokenType: String,
    val expiresIn: Int,
    val accessToken: String,
    val scope: String,
    val refreshToken: String
)