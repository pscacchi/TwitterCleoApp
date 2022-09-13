package ar.scacchipa.twittercloneapp.utils

class Constants {
    companion object {
        const val CLIENT_ID = "Yzg1a01Hcm16RTdKdmptZmhJdEs6MTpjaQ"
        const val REDIRECT_URI = "https://twittercloneendava.firebaseapp.com/__/auth/handler"

        const val RESPONSE_TYPE_CODE = "code"
        const val STATE_STATE = "state"
        const val CODE_CHALLENGE_CHALLENGE = "challenge"
        const val CODE_CHALLENGE_METHOD_PLAIN = "plain"
        const val TWITTER_SESSION = "twitter_session"
        const val TOKEN_TYPE = "tokenType"
        const val ACCESS_TOKEN = "access_token"
        const val SCOPE = "scope"
        const val REFRESH_TOKEN = "refreshToken"

        const val GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code"
        const val CODE_VERIFIER_CHALLENGE = "challenge"

        const val SERVER_PARAMETER_CODE = "code"
        const val SERVER_PARAMETER_ERROR = "error"

        const val ERROR_HOST_LOOKUP_TOKEN = "error_host_lookup"
        const val ERROR_ACCESS_DENIED = "access_denied"
        const val ERROR_NO_REVOKE_ACCESS_TOKEN = "error_no_revoke_access_token"

        const val SCOPE_USERS_READ = "users.read"
        const val SCOPE_TWEET_READ = "tweet.read"
        const val SCOPE_TWEET_WRITE = "tweet.write"
        const val SCOPE_OFFLINE_ACCESS = "offline.access"
        const val SCOPE_LIST_READ = "list.read"
        const val SCOPE_FOLLOWS_READ = "follows.read"
        const val SCOPE_LIKE_READ = "like.read"
        const val SCOPE_LIKE_WRITE = "like.write"
        const val SCOPE_SPACE_READ = "space.read"
    }
}