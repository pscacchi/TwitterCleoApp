package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.data.Credential
import ar.scacchipa.twittercloneapp.repository.ICredentialRepository

class StoreCredentialUseCase(
    private val credentialRepository: ICredentialRepository
): IStoreCredentialUseCase {
    override suspend operator fun invoke(credentials: Credential): Boolean {
        return credentialRepository.storeCredentials( credentials )
    }
}

