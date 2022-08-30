package ar.scacchipa.twittercloneapp.data.repository

sealed class ResponseDomain{

    data class Success<T>(
        val data: T
    ): ResponseDomain()

    data class Error(
        val error: String = "",
        val errorDescription: String = ""
    ): ResponseDomain()

    data class Exception(
        val message: String = ""
    ) : ResponseDomain()

    object Cancel : ResponseDomain()
}
