package ar.scacchipa.twittercloneapp.data.repository

import ar.scacchipa.twittercloneapp.domain.model.ResponseDomain

interface ITweetRepository {
    suspend fun getTweets() : ResponseDomain
}
