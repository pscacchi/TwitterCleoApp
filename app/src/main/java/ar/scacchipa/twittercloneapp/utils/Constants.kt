package ar.scacchipa.twittercloneapp.utils

class Constants {
    companion object {
        const val CLIENT_ID = "Yzg1a01Hcm16RTdKdmptZmhJdEs6MTpjaQ"
        const val REDIRECT_URI = "https://twittercloneendava.firebaseapp.com/__/auth/handler"

        const val RESPONSE_TYPE_CODE = "code"
        const val STATE = "state"
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
        const val TWEETS_DOWNLOAD_ERROR = "TWEETS_DOWNLOAD_ERROR"
        const val TWEETS_DOWNLOAD_ERROR_TXT = "Failed to download tweet list."
        const val NO_OWNER_USER_DATA_ERROR = "NO_OWNER_USER_ERROR"
        const val NO_OWNER_USER_DATA_ERROR_TXT = "Failed to recovered the stored owner user data"

        const val OWNER_USER_DATA_ID = "owner_user_data_id"
        const val OWNER_USER_DATA_VERIFIED = "owner_user_data_verified"
        const val OWNER_USER_DATA_NAME = "owner_user_data_name"
        const val OWNER_USER_DATA_USERNAME = "owner_user_data_username"
        const val OWNER_USER_DATA_PROFILE_IMAGE_URL = "owner_user_data_profileImageUrl"



        const val SCOPE_USERS_READ = "users.read"
        const val SCOPE_TWEET_READ = "tweet.read"
        const val SCOPE_TWEET_WRITE = "tweet.write"
        const val SCOPE_OFFLINE_ACCESS = "offline.access"
        const val SCOPE_LIST_READ = "list.read"
        const val SCOPE_FOLLOWS_READ = "follows.read"
        const val SCOPE_LIKE_READ = "like.read"
        const val SCOPE_LIKE_WRITE = "like.write"
        const val SCOPE_SPACE_READ = "space.read"

        const val ACTIVITY_ACTION_SKIP_SPLASH = "skipSplashView"

        const val RETWEETED_MSG = " retweeted"
        const val REPLIED_TO_MSG = "Replied to "
        const val QUOTED_BY_MSG = "Quoted by "

    }
}