package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.data.ResponseDomain
import ar.scacchipa.twittercloneapp.data.RevokeDomain
import ar.scacchipa.twittercloneapp.repository.ICredentialLocalRepository
import ar.scacchipa.twittercloneapp.repository.IRevokeTokenRepository
import ar.scacchipa.twittercloneapp.utils.Constants

class RevokeCredentialUseCase(
    private val repository: IRevokeTokenRepository,
    private val credentialLocalRepo: ICredentialLocalRepository
): IRevokeCredentialUseCase {
    override suspend operator fun invoke(): Boolean {
        val response = repository.revokeToken(
            token = credentialLocalRepo.recoverCredential().accessToken,
            clientId = Constants.CLIENT_ID
        )
        return if (response is ResponseDomain.Success<*>
            && (response.data as RevokeDomain).revoked
        ) {
            credentialLocalRepo.removeCredential()
            true
        } else {
            false
        }
    }
}

