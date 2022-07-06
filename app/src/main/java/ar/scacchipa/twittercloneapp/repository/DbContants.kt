package ar.scacchipa.twittercloneapp.repository

object DbContants {
    const val CLIENT_ID = "Yzg1a01Hcm16RTdKdmptZmhJdEs6MTpjaQ"
    const val REDIRECT_URI = "https://twittercloneendava.firebaseapp.com/__/auth/handler"

    const val RESPONSE_TYPE_CODE = "code"
    const val STATE_STATE = "state"
    const val CODE_CHALLENGE_CHALLENGE = "challenge"
    const val CODE_CHALLENGE_METHOD_PLAIN = "plain"

    const val GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code"
    const val CODE_VERIFIER_CHALLENGE = "challenge"

    const val SCOPE_USERS_READ = "users.read"
    const val SCOPE_TWEET_READ = "tweet.read"
    const val SCOPE_TWEET_WRTIE = "tweet.write"
    const val SCOPE_OFFLINE_ACCESS = "offline.access"
    const val SCOPE_LIST_READ = "list.read"
    const val SCOPE_FOLLOWS_READ = "follows.read"
    const val SCOPE_LIKE_READ = "like.read"
    const val SCOPE_LIKE_WRITE = "like.write"
    const val SCOPE_SPACE_READ = "space.read"

}