package ar.scacchipa.twittercloneapp.utils

import ar.scacchipa.twittercloneapp.data.Credential
import ar.scacchipa.twittercloneapp.data.UserAccessTokenData

class MockTokenProvider {
    companion object {
        fun credential1(): Credential {
            return Credential(
                accessToken = "OU1tZ2dUanRYMjhGUEVnOUlHUGlYUUlyWVI3Ukhpd1gweW9ET051OW9HR2hTOjE2NTY1OTUxOTIxMTU6MToxOmF0OjE",
                refreshToken = "LVJQQXMxSUM0QUQ2eHNidkNfYUNScUJoSTY5Sy1ndGxqMmx2WnRPQzF4NklDOjE2NTY1OTUxOTIxMTU6MTowOnJ0OjE"
            )
        }
        fun credentialNull(): Credential {
            return Credential("", "")
        }

        fun userAccessTokenData1(): UserAccessTokenData {
            return UserAccessTokenData(
                tokenType = "bearer",
                expiresIn = 7200,
                accessToken = "OU1tZ2dUanRYMjhGUEVnOUlHUGlYUUlyWVI3Ukhpd1gweW9ET051OW9HR2hTOjE2NTY1OTUxOTIxMTU6MToxOmF0OjE",
                scope = "tweet.read tweet.write",
                refreshToken = "LVJQQXMxSUM0QUQ2eHNidkNfYUNScUJoSTY5Sy1ndGxqMmx2WnRPQzF4NklDOjE2NTY1OTUxOTIxMTU6MTowOnJ0OjE",
                errorDescription = "")
        }
        fun mappedUserAccessTokenData1(): UserAccessTokenData {
            return UserAccessTokenData(
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