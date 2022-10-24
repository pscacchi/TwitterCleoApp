package ar.scacchipa.twittercloneapp.data.repository

import ar.scacchipa.twittercloneapp.data.model.UserData

interface ILoggedUserRepository {
    suspend fun refreshLoggedUser(): Boolean
    suspend fun getLoggedUser(): UserData?
}

