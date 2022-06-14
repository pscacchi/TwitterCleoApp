package ar.scacchipa.twittercloneapp.ui

sealed class Route(val route: String) {
    object SplashScreen: Route("splash")
    object LoginScreen: Route("login")
}