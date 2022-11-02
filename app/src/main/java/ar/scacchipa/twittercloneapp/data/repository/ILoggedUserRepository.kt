package ar.scacchipa.twittercloneapp.data.repository

import ar.scacchipa.twittercloneapp.domain.model.UserInfo

interface ILoggedUserRepository {
    suspend fun refreshLoggedUser(accessToken: String): Boolean
    suspend fun getLoggedUser(): UserInfo?
}

