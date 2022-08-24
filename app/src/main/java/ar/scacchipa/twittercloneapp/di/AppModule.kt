package ar.scacchipa.twittercloneapp.di

import android.content.SharedPreferences
import ar.scacchipa.twittercloneapp.data.Credential
import ar.scacchipa.twittercloneapp.data.IMapper
import ar.scacchipa.twittercloneapp.data.UserAccessTokenData
import ar.scacchipa.twittercloneapp.data.UserAccessTokenMapper
import ar.scacchipa.twittercloneapp.datasource.IAuthDataSource
import ar.scacchipa.twittercloneapp.domain.*
import ar.scacchipa.twittercloneapp.fragment.FragmentAuthWebDialog
import ar.scacchipa.twittercloneapp.fragment.FragmentLogin
import ar.scacchipa.twittercloneapp.fragment.FragmentSplash
import ar.scacchipa.twittercloneapp.repository.AuthorizationRepository
import ar.scacchipa.twittercloneapp.repository.CredentialRepository
import ar.scacchipa.twittercloneapp.repository.IAuthorizationRepository
import ar.scacchipa.twittercloneapp.repository.ICredentialRepository
import ar.scacchipa.twittercloneapp.viewmodel.AuthWebDialogViewModel
import ar.scacchipa.twittercloneapp.viewmodel.LoginViewModel
import ar.scacchipa.twittercloneapp.viewmodel.SplashViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { provideRetrofit() }
    single { provideCredentialLocalSource( androidApplication() ) as SharedPreferences }
    single { provideAuthSourceDataApi(get()) as IAuthDataSource }

    single { UserAccessTokenMapper() as IMapper<UserAccessTokenData, Credential> }

    single { AuthorizationRepository(get(), get()) as IAuthorizationRepository }
    single { CredentialRepository( get() ) as ICredentialRepository }

    single { AuthorizationUseCase(get()) }
    single { ConsumableAuthUseCase() }
    single { SplashTimerUseCase() }
    single { StoreCredentialUseCase( get() ) as IStoreCredentialUseCase }
    single { CheckCredentialUseCase( get() ) as ICheckCredentialUseCase }

    viewModel { AuthWebDialogViewModel( get(), get(), get() ) }
    viewModel { LoginViewModel( ) }
    viewModel { SplashViewModel( get(), get() ) }

    fragment { FragmentAuthWebDialog() }
    fragment { FragmentLogin() }
    fragment { FragmentSplash() }
}