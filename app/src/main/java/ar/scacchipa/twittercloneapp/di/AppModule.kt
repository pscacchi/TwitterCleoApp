package ar.scacchipa.twittercloneapp.di

import ar.scacchipa.twittercloneapp.data.IMapper
import ar.scacchipa.twittercloneapp.data.datasource.UserAccessTokenData
import ar.scacchipa.twittercloneapp.data.repository.UserAccessTokenDataMapper
import ar.scacchipa.twittercloneapp.data.repository.UserAccessTokenDomain
import ar.scacchipa.twittercloneapp.data.datasource.IAuthDataSource
import ar.scacchipa.twittercloneapp.domain.AuthorizationUseCase
import ar.scacchipa.twittercloneapp.domain.ConsumableAuthUseCase
import ar.scacchipa.twittercloneapp.domain.SplashTimerUseCase
import ar.scacchipa.twittercloneapp.presentation.authwebdialog.FragmentAuthWebDialog
import ar.scacchipa.twittercloneapp.presentation.login.FragmentLogin
import ar.scacchipa.twittercloneapp.presentation.splash.FragmentSplash
import ar.scacchipa.twittercloneapp.data.repository.AuthorizationRepository
import ar.scacchipa.twittercloneapp.data.repository.IAuthorizationRepository
import ar.scacchipa.twittercloneapp.presentation.authwebdialog.AuthWebDialogViewModel
import ar.scacchipa.twittercloneapp.presentation.login.LoginViewModel
import ar.scacchipa.twittercloneapp.presentation.splash.SplashViewModel
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { provideRetrofit() }

    single { UserAccessTokenDataMapper() as IMapper<UserAccessTokenData, UserAccessTokenDomain>}

    single { provideAuthSourceDateApi( get() ) as IAuthDataSource }
    single { AuthorizationRepository( get(), get() ) as IAuthorizationRepository }

    single { AuthorizationUseCase( get() ) }
    single { ConsumableAuthUseCase() }
    single { SplashTimerUseCase() }


    viewModel { AuthWebDialogViewModel( get(), get() ) }
    viewModel { LoginViewModel() }
    viewModel { SplashViewModel( get() ) }

    fragment { FragmentAuthWebDialog() }
    fragment { FragmentLogin() }
    fragment { FragmentSplash() }
}