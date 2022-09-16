package ar.scacchipa.twittercloneapp.domain.usecase

import ar.scacchipa.twittercloneapp.data.repository.ICredentialRepository

class RevokeCredentialUseCase(
    private val credentialRepository: ICredentialRepository
) {
    suspend operator fun invoke(): Boolean {
        return credentialRepository.revokeCredential()
    }
}