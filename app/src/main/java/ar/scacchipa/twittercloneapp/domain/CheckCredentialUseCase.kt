package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.repository.ICredentialLocalRepository

class CheckCredentialUseCase(
    private val credentialRepository: ICredentialLocalRepository
): ICheckCredentialUseCase {
    override operator fun invoke(): Boolean {
        credentialRepository.recoverCredential().apply {
            return accessToken.isNotEmpty() &&
                    refreshToken.isNotEmpty()
        }
    }
}