package ar.scacchipa.twittercloneapp.presentation.splash

sealed class SplashState {
    object ShowLogo: SplashState()
    object GoToLogin:SplashState()
    object SkipLogin: SplashState()
}