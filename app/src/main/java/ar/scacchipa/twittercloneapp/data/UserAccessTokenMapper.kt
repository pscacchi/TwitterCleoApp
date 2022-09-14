package ar.scacchipa.twittercloneapp.data

import ar.scacchipa.twittercloneapp.data.model.UserAccessToken
import ar.scacchipa.twittercloneapp.domain.model.Credential
import ar.scacchipa.twittercloneapp.utils.Constants

class UserAccessTokenMapper: IMapper<UserAccessToken, Credential> {
    override fun toDomain(value: UserAccessToken): Credential {
        return Credential(
            accessToken = value.accessToken,
            refreshToken = value.refreshToken )
    }
    override fun fromDomain(value: Credential): UserAccessToken {
        return UserAccessToken(
            tokenType = Constants.TOKEN_TYPE,
            expiresIn = 7200,
            accessToken = value.accessToken,
            scope = Constants.SCOPE,
            refreshToken = value.refreshToken,
            errorDescription = "" )
    }
}