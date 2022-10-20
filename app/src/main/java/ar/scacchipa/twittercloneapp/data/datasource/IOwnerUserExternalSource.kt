package ar.scacchipa.twittercloneapp.data.datasource

import ar.scacchipa.twittercloneapp.data.model.response.UserDataWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface IOwnerUserExternalSource {
    @GET(
        "2/users/me?"
                + "tweet.fields=public_metrics"
                + "&user.fields=id,profile_image_url,verified"
    )
    suspend fun getOwnerUserData(
        @Header("Authorization") bearerCode: String
    ): Response<UserDataWrapper>
}