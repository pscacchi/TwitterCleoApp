package ar.scacchipa.twittercloneapp.di

import ar.scacchipa.twittercloneapp.data.datasource.IAuthDataSource
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://api.twitter.com/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
}

fun provideAuthSourceDataApi(retrofit: Retrofit): IAuthDataSource =
    retrofit.create(IAuthDataSource::class.java)
