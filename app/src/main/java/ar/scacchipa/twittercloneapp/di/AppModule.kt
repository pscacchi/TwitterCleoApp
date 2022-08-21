package ar.scacchipa.twittercloneapp.di

import ar.scacchipa.twittercloneapp.data.IMapper
import ar.scacchipa.twittercloneapp.data.UserAccessTokenData
import ar.scacchipa.twittercloneapp.data.UserAccessTokenDataMapper
import ar.scacchipa.twittercloneapp.data.UserAccessTokenDomain
import ar.scacchipa.twittercloneapp.datasource.IAuthDataSource
import ar.scacchipa.twittercloneapp.domain.*
import ar.scacchipa.twittercloneapp.fragment.FragmentAuthWebDialog
import ar.scacchipa.twittercloneapp.fragment.FragmentLogin
import ar.scacchipa.twittercloneapp.fragment.FragmentSplash
import ar.scacchipa.twittercloneapp.repository.AuthorizationRepository
import ar.scacchipa.twittercloneapp.repository.IAuthorizationRepository
import ar.scacchipa.twittercloneapp.viewmodel.AuthWebDialogViewModel
import ar.scacchipa.twittercloneapp.viewmodel.LoginViewModel
import ar.scacchipa.twittercloneapp.viewmodel.SplashViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { provideRetrofit() }

    single { UserAccessTokenDataMapper() as IMapper<UserAccessTokenData, UserAccessTokenDomain> }

    single { provideAuthSourceDataApi(get()) as IAuthDataSource }
    single { AuthorizationRepository(get(), get()) as IAuthorizationRepository }

    single { AuthorizationUseCase(get()) }
    single { ConsumableAuthUseCase() }
    single { SplashTimerUseCase() }

    single { getSharedPrefs( androidApplication() ) }
    single { SavedAccessTokenUseCase( get() ) }
    single { RecoveredAccessTokenUseCase( get() )}

    viewModel { AuthWebDialogViewModel( get(), get(), get() ) }
    viewModel { LoginViewModel( ) }
    viewModel { SplashViewModel( get(), get() ) }

    fragment { FragmentAuthWebDialog() }
    fragment { FragmentLogin() }
    fragment { FragmentSplash() }
}