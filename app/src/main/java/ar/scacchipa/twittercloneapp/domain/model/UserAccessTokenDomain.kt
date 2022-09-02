package ar.scacchipa.twittercloneapp.domain.model

data class UserAccessTokenDomain(
    val tokenType: String,
    val expiresIn: Int,
    val accessToken: String,
    val scope: String,
    val refreshToken: String
)