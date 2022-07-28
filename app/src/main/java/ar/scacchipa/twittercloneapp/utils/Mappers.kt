package ar.scacchipa.twittercloneapp.utils

import ar.scacchipa.twittercloneapp.data.TokenResource
import ar.scacchipa.twittercloneapp.data.UserAccessToken
import retrofit2.Response

fun Response<UserAccessToken>.toTokenResource(): TokenResource {
    return if (this.isSuccessful) {
        this.body()?.let { userAccessToken ->
            TokenResource.Success(
                tokenType = userAccessToken.tokenType,
                expiresIn = userAccessToken.expiresIn,
                accessToken = userAccessToken.accessToken,
                scope = userAccessToken.scope,
                refreshToken = userAccessToken.refreshToken
            )
        } ?: TokenResource.Error()
    } else {
        TokenResource.Error(
            error = Constants.ERROR_HOST_LOOKUP_TOKEN,
            errorDescription = this.body()?.errorDescription ?: ""
        )
    }
}