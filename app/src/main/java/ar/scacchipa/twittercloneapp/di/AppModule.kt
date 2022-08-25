package ar.scacchipa.twittercloneapp.di

import android.content.SharedPreferences
import ar.scacchipa.twittercloneapp.data.IRevokeMapper
import ar.scacchipa.twittercloneapp.data.IUserAccessTokenMapper
import ar.scacchipa.twittercloneapp.data.RevokeMapper
import ar.scacchipa.twittercloneapp.data.UserAccessTokenMapper
import ar.scacchipa.twittercloneapp.datasource.IAuthDataSource
import ar.scacchipa.twittercloneapp.datasource.IRevokeTokenDataSource
import ar.scacchipa.twittercloneapp.domain.*
import ar.scacchipa.twittercloneapp.fragment.FragmentAuthWebDialog
import ar.scacchipa.twittercloneapp.fragment.FragmentLogin
import ar.scacchipa.twittercloneapp.fragment.FragmentSplash
import ar.scacchipa.twittercloneapp.repository.*
import ar.scacchipa.twittercloneapp.viewmodel.AuthWebDialogViewModel
import ar.scacchipa.twittercloneapp.viewmodel.LoginViewModel
import ar.scacchipa.twittercloneapp.viewmodel.MainActivityViewModel
import ar.scacchipa.twittercloneapp.viewmodel.SplashViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { provideRetrofit() }
    single { provideAuthSourceDataApi( get() ) as IAuthDataSource }
    single { provideRevokeTokenSourceDataApi( get() ) as IRevokeTokenDataSource }
    single { provideCredentialLocalSource( androidApplication() ) as SharedPreferences }

    single { UserAccessTokenMapper() as IUserAccessTokenMapper }
    single { RevokeMapper() as IRevokeMapper }

    single { AuthorizationRepository(get(), get()) as IAuthorizationRepository }
    single { CredentialLocalRepository( get() ) as ICredentialLocalRepository }
    single { RevokeTokenRepository( get(), get() ) as IRevokeTokenRepository }

    single { SplashTimerUseCase() }
    single { ConsumableAuthUseCase() }
    single { AuthorizationUseCase( get() ) }
    single { StoreCredentialUseCase( get() ) as IStoreCredentialUseCase }
    single { CheckCredentialUseCase( get() ) as ICheckCredentialUseCase }
    single { RevokeCredentialUseCase( get(), get() ) as IRevokeCredentialUseCase }

    single { SplashViewModel( get(), get() ) }
    viewModel { LoginViewModel( ) }
    viewModel { AuthWebDialogViewModel( get(), get(), get() ) }
    viewModel { MainActivityViewModel( get() ) }

    fragment { FragmentAuthWebDialog() }
    fragment { FragmentLogin() }
    fragment { FragmentSplash() }
}