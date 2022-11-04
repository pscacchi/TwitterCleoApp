package ar.scacchipa.twittercloneapp.presentation.main.home

sealed interface State<T> {
    class Success<T>(
        val data: T
    ) : State<T>

    class Error<T>(
        val error: String,
        val description: String
    ) : State<T>

    class Loading<T> : State<T>
}