package ar.scacchipa.twittercloneapp.domain.model

data class TweetCardInfo(
    val text: String,
    val publicMetrics: PublicMetricInfo,
    val authorId: String,
    val profileImageUrl: String,
    var name: String,
    var username: String,
    var verified: Boolean,
    val referenceTweet: ReferencedType,
    val profileBitmapByteArray: ByteArray? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TweetCardInfo

        if (text != other.text
            || publicMetrics != other.publicMetrics
            || authorId != other.authorId
            || profileImageUrl != other.profileImageUrl
            || name != other.name
            || username != other.username
            || verified != other.verified
            || referenceTweet != other.referenceTweet) return false
        if (profileBitmapByteArray != null) {
            if (other.profileBitmapByteArray == null) return false
            if (!profileBitmapByteArray.contentEquals(other.profileBitmapByteArray)) return false
        } else if (other.profileBitmapByteArray != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = text.hashCode()
        result = 31 * result + publicMetrics.hashCode()
        result = 31 * result + authorId.hashCode()
        result = 31 * result + profileImageUrl.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + verified.hashCode()
        result = 31 * result + referenceTweet.hashCode()
        result = 31 * result + (profileBitmapByteArray?.contentHashCode() ?: 0)
        return result
    }
}

