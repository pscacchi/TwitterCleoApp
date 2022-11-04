package ar.scacchipa.twittercloneapp.domain.usecase

import ar.scacchipa.twittercloneapp.data.repository.ICredentialRepository
import ar.scacchipa.twittercloneapp.data.repository.ILoggedUserRepository
import ar.scacchipa.twittercloneapp.domain.model.Credential
import ar.scacchipa.twittercloneapp.domain.model.ResponseDomain
import ar.scacchipa.twittercloneapp.utils.Constants

open class AuthorizationUseCase(
    private val credentialRepository: ICredentialRepository,
    private val loggedUserRepository: ILoggedUserRepository
) {
    suspend operator fun invoke(
        transitoryToken: String
    ): ResponseDomain {
        val accessTokenRequest = credentialRepository.getExternalCredential(
            transitoryToken = transitoryToken,
            grantType = Constants.GRANT_TYPE_AUTHORIZATION_CODE,
            clientId = Constants.CLIENT_ID,
            redirectUri = Constants.REDIRECT_URI,
            codeVerifier = Constants.CODE_VERIFIER_CHALLENGE,
            state = Constants.STATE
        )

        if (accessTokenRequest is ResponseDomain.Success<*>) {
            val credential = accessTokenRequest.data as Credential
            credentialRepository.storeLocalCredential(credential)
            if (loggedUserRepository.refreshLoggedUser(credential.accessToken).not()) {
                return ResponseDomain.Error(
                    error = Constants.NO_LOGGED_USER_DATA_ERROR,
                    errorDescription = Constants.NO_LOGGED_USER_DATA_ERROR_TXT
                )
            }
        }

        return accessTokenRequest
    }
}
