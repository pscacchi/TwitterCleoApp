package ar.scacchipa.twittercloneapp.data

import ar.scacchipa.twittercloneapp.utils.Constants

class UserAccessTokenMapper: IUserAccessTokenMapper {
    override fun toDomain(value: UserAccessTokenData): Credential {
        return Credential(
            accessToken = value.accessToken,
            refreshToken = value.refreshToken )
    }
    override fun fromDomain(value: Credential): UserAccessTokenData {
        return UserAccessTokenData(
            tokenType = Constants.TOKEN_TYPE,
            expiresIn = 7200,
            accessToken = value.accessToken,
            scope = Constants.SCOPE,
            refreshToken = value.refreshToken,
            errorDescription = "" )
    }
}

