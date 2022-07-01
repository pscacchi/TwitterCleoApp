package ar.scacchipa.twittercloneapp.datasource

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface IAuthDataSource {
    @FormUrlEncoded
    @POST("2/oauth2/token")
    suspend fun genAccessTokenSourceCode(
        @Field("code") transitoryToken: String,
        @Field("grant_type") grant_type: String = "authorization_code",
        @Field("client_id") clientId: String,
        @Field("redirect_uri") redirect_uri: String,
        @Field("code_verifier") codeVerifier: String = "challenge",
        @Field("state") state:String = "state"
    ) : Response<UserAccessToken>
}