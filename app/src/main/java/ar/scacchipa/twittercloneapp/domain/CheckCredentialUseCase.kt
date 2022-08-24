package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.repository.ICredentialRepository

class CheckCredentialUseCase(
    private val credentialRepository: ICredentialRepository
): ICheckCredentialUseCase {
    override operator fun invoke(): Boolean {
        credentialRepository.recoverCredential().apply {
            return accessToken.isNotEmpty() &&
                    refreshToken.isNotEmpty()
        }
    }
}