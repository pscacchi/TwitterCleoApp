package ar.scacchipa.twittercloneapp.datasource

import ar.scacchipa.twittercloneapp.repository.Constants
import com.google.gson.annotations.SerializedName

data class UserAccessToken (
    @SerializedName("token_type") val tokenType: String = "",
    @SerializedName("expires_in") val expiresIn: Int = 0,
    @SerializedName("access_token") val accessToken: String = "",
    @SerializedName("scope") val scope: String = "",
    @SerializedName("refresh_token") val refreshToken: String = "",
    @SerializedName("") val error: String = "",
    @SerializedName("error_description") val errorDescription: String = ""
) {
    fun isCancellationErrorToken(): Boolean {
        return error == Constants.ERROR_CANCELLED_AUTH
    }
    fun isHostLookupErrorToken(): Boolean {
        return error == Constants.ERROR_HOST_LOOKUP_TOKEN
    }
    fun isSuccessToken(): Boolean {
        return accessToken.isNotBlank()
    }
}
