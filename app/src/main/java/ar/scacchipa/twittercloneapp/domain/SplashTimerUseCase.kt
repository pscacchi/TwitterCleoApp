package ar.scacchipa.twittercloneapp.domain

import kotlinx.coroutines.delay

open class SplashTimerUseCase {
    open suspend fun spendSplash(): Boolean {
        delay(5000)
        return true
    }
}