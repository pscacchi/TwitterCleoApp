package ar.scacchipa.twittercloneapp.data.datasource

import ar.scacchipa.twittercloneapp.data.model.RevokeData
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface IRevokeTokenDataSource {
    @FormUrlEncoded
    @POST("2/oauth2/revoke")
    suspend fun revokeToken(
        @Field("token") token: String,
        @Field("client_id") clientId: String,
        @Field("token_type_hint") tokenTypeHint: String
    ): Response<RevokeData>
}