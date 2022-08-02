package ar.scacchipa.twittercloneapp.data

class UserAccessTokenDataMapper: IMapper<UserAccessTokenData, UserAccessTokenDomain> {
    override fun toDomain(value: UserAccessTokenData): UserAccessTokenDomain {
        return UserAccessTokenDomain(
            tokenType = value.tokenType,
            expiresIn = value.expiresIn,
            accessToken = value.accessToken,
            scope = value.scope,
            refreshToken = value.refreshToken )
    }
    override fun fromDomain(value: UserAccessTokenDomain): UserAccessTokenData {
        return UserAccessTokenData(
            tokenType = value.tokenType,
            expiresIn = value.expiresIn,
            accessToken = value.accessToken,
            scope = value.scope,
            refreshToken = value.refreshToken,
            errorDescription = "" )
    }
}