package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.repository.ICredentialRepository

class CheckCredentialUseCase(
    private val credentialRepository: ICredentialRepository
): ICheckCredentialUseCase {
    override operator fun invoke(): Boolean {
        credentialRepository.recoverCredentials().apply {
            return accessToken.isNotEmpty() &&
                    refreshToken.isNotEmpty()
        }
    }
}