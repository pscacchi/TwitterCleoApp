package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.datasource.UserAccessToken

class NavToLoginWithoutMsgUseCase {
    operator fun invoke(): UserAccessToken {
        return UserAccessToken(error = "")
    }
}