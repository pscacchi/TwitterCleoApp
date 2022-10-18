package ar.scacchipa.twittercloneapp.domain.model

data class TweetInfo(
    val text: String,
    val publicMetrics: PublicMetricInfo,
    val authorId: String,
    val profileImageUrl: String,
    var name: String,
    var username: String,
    var verified: Boolean,
    val referenceTweet: ReferencedType,
    val profileBitmapByteArray: ByteArray? = null
)

