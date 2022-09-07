package ar.scacchipa.twittercloneapp.domain.usecase

import ar.scacchipa.twittercloneapp.data.repository.ICredentialRepository
import kotlinx.coroutines.delay

open class StarterUseCase (
    private val credentialRepository: ICredentialRepository
) {
    suspend operator fun invoke(): Boolean {
        delay(5000)
        return credentialRepository.recoverLocalCredential() == null
    }
}

