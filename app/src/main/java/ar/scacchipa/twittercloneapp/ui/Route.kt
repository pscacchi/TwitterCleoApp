package ar.scacchipa.twittercloneapp.ui

sealed class Route(val route: String) {
    object Splash: Route("splash")
    object Login: Route("login")
}