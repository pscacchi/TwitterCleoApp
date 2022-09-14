package ar.scacchipa.twittercloneapp.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import ar.scacchipa.twittercloneapp.utils.Constants

fun provideCredentialLocalSource(androidApplication: Application): SharedPreferences {
    return androidApplication
        .getSharedPreferences(Constants.TWITTER_SESSION, Context.MODE_PRIVATE)
}
