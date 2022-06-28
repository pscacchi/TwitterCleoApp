package ar.scacchipa.twittercloneapp.domain

import kotlinx.coroutines.delay

class SplashTimerUseCase {
    suspend fun spendSplash(): Boolean {
        delay(5000)
        return true
    }
}