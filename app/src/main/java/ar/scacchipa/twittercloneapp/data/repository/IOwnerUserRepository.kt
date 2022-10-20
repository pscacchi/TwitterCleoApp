package ar.scacchipa.twittercloneapp.data.repository

import ar.scacchipa.twittercloneapp.data.model.UserData

interface IOwnerUserRepository {
    suspend fun refreshOwnerUser(): Boolean
    suspend fun getOwnerUser(): UserData?
}

