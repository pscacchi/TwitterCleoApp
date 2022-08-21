package ar.scacchipa.twittercloneapp.repository

import ar.scacchipa.twittercloneapp.data.ResponseDomain

interface IRevokeTokenRepository {
    suspend fun revokeToken(
        token: String,
        clientId: String
    ): ResponseDomain
}