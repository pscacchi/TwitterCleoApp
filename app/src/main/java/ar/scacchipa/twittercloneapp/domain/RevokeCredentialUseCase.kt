package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.data.ResponseDomain
import ar.scacchipa.twittercloneapp.data.RevokeDomain
import ar.scacchipa.twittercloneapp.repository.ICredentialRepository
import ar.scacchipa.twittercloneapp.repository.IRevokeTokenRepository

class RevokeCredentialUseCase(
    private val repository: IRevokeTokenRepository,
    private val credentialLocalRepo: ICredentialRepository
) {
    suspend operator fun invoke(
        token: String,
        clientId: String
    ) {
        val response = repository.revokeToken(
            token = token,
            clientId = clientId
        )
        when (response) {
            is ResponseDomain.Success<*> -> {
                val revokeDomain = response.data as RevokeDomain

                if (revokeDomain.revoked) {
                    credentialLocalRepo.removeCredential()
                }
            }
            else ->
                return
        }
    }
}