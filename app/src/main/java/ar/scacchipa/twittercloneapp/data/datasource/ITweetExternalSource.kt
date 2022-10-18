package ar.scacchipa.twittercloneapp.data.datasource

import ar.scacchipa.twittercloneapp.data.model.tweet.TweetsDataWrapper
import ar.scacchipa.twittercloneapp.data.model.me.UserMeWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ITweetExternalSource {
    @GET("2/users/{id}/timelines/reverse_chronological?"
            + "expansions=author_id,referenced_tweets.id,referenced_tweets.id.author_id"
            + "&tweet.fields=public_metrics"
            + "&user.fields=id,profile_image_url,verified")

    suspend fun getTweets(
        @Path("id")  userId: String,
        @Header("Authorization") bearerCode: String
    ) : Response<TweetsDataWrapper>

    @GET("2/users/me")
    suspend fun getUserMeData(
        @Header("Authorization") bearerCode: String
    ): Response<UserMeWrapper>
}

