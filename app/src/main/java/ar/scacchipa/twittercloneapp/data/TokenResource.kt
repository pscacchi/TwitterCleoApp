package ar.scacchipa.twittercloneapp.data

sealed class TokenResource {

    data class Success(
        val tokenType: String = "",
        val expiresIn: Int = 0,
        val accessToken: String = "",
        val scope: String = "",
        val refreshToken: String = "",
    ): TokenResource()

    data class Error(
        val error: String = "",
        val errorDescription: String = ""
    ): TokenResource()

    data class Exception(
        val message: String = ""
    ) : TokenResource()

    object Cancel : TokenResource()
}
