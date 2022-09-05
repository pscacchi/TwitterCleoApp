package ar.scacchipa.twittercloneapp.utils

import ar.scacchipa.twittercloneapp.data.model.UserAccessToken
import ar.scacchipa.twittercloneapp.domain.model.Credential

class MockTokenProvider {
    companion object {
        fun transitoryToken1(): String {
            return "SGVvLWIyclkweEJudVZWSFFyR3RqQUVadEdlSFZJRk1JLXRacllVb3BxRFhhOjE2NTcxMTQyMDA2ODY6MTowOmFjOjE"
        }
        fun incorrectTransitoryToken(): String {
            return "Incorrect transitory token"
        }
        fun credential1(): Credential {
            return Credential(
                accessToken = "OU1tZ2dUanRYMjhGUEVnOUlHUGlYUUlyWVI3Ukhpd1gweW9ET051OW9HR2hTOjE2NTY1OTUxOTIxMTU6MToxOmF0OjE",
                refreshToken = "LVJQQXMxSUM0QUQ2eHNidkNfYUNScUJoSTY5Sy1ndGxqMmx2WnRPQzF4NklDOjE2NTY1OTUxOTIxMTU6MTowOnJ0OjE"
            )
        }
        fun credentialNull(): Credential {
            return Credential("", "")
        }

        fun userAccessTokenData1(): UserAccessToken {
            return UserAccessToken(
                tokenType = "bearer",
                expiresIn = 7200,
                accessToken = "OU1tZ2dUanRYMjhGUEVnOUlHUGlYUUlyWVI3Ukhpd1gweW9ET051OW9HR2hTOjE2NTY1OTUxOTIxMTU6MToxOmF0OjE",
                scope = "tweet.read tweet.write",
                refreshToken = "LVJQQXMxSUM0QUQ2eHNidkNfYUNScUJoSTY5Sy1ndGxqMmx2WnRPQzF4NklDOjE2NTY1OTUxOTIxMTU6MTowOnJ0OjE",
                errorDescription = "")
        }
        fun mappedUserAccessTokenData1(): UserAccessToken {
            return UserAccessToken(
                tokenType = Constants.TOKEN_TYPE,
                expiresIn = 7200,
                accessToken = "OU1tZ2dUanRYMjhGUEVnOUlHUGlYUUlyWVI3Ukhpd1gweW9ET051OW9HR2hTOjE2NTY1OTUxOTIxMTU6MToxOmF0OjE",
                scope = Constants.SCOPE,
                refreshToken = "LVJQQXMxSUM0QUQ2eHNidkNfYUNScUJoSTY5Sy1ndGxqMmx2WnRPQzF4NklDOjE2NTY1OTUxOTIxMTU6MTowOnJ0OjE",
                errorDescription = ""
            )
        }
    }
}