package ar.scacchipa.twittercloneapp.data.datasource

import ar.scacchipa.twittercloneapp.data.model.RevokeData
import ar.scacchipa.twittercloneapp.data.model.UserAccessToken
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface IAuthExternalSource {
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

    @FormUrlEncoded
    @POST("2/oauth2/revoke")
    suspend fun revokeToken(
        @Field("token") token: String,
        @Field("client_id") clientId: String,
        @Field("token_type_hint") tokenTypeHint: String
    ): Response<RevokeData>
}