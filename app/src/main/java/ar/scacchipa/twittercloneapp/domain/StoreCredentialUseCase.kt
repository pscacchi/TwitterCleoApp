package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.data.Credential
import ar.scacchipa.twittercloneapp.repository.ICredentialLocalRepository

class StoreCredentialUseCase(
    private val credentialRepository: ICredentialLocalRepository
): IStoreCredentialUseCase {
    override suspend operator fun invoke(credential: Credential): Boolean {
        return credentialRepository.storeCredential( credential )
    }
}

