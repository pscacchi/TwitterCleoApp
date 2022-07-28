package ar.scacchipa.twittercloneapp.datasource

import ar.scacchipa.twittercloneapp.data.UserAccessToken
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface IAuthDataSource {
    @FormUrlEncoded
    @POST("2/oauth2/token")
    suspend fun genAccessTokenSourceCode(
        @Field("code") transitoryToken: String,
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String,
        @Field("redirect_uri") redirectUri: String,
        @Field("code_verifier") codeVerifier: String,
        @Field("state") state:String
    ) : Response<UserAccessToken>
}