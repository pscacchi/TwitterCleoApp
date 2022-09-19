package ar.scacchipa.twittercloneapp.domain.usecase

import ar.scacchipa.twittercloneapp.data.repository.ICredentialRepository
import ar.scacchipa.twittercloneapp.presentation.splash.SplashState
import ar.scacchipa.twittercloneapp.presentation.splash.SplashState.GoToLogin
import ar.scacchipa.twittercloneapp.presentation.splash.SplashState.SkipLogin
import kotlinx.coroutines.delay

open class StarterUseCase (
    private val credentialRepository: ICredentialRepository
) {
    suspend operator fun invoke(): SplashState {
        delay(5000)
        return if (credentialRepository.recoverLocalCredential() == null) {
            GoToLogin
        } else {
            SkipLogin
        }
    }
}

