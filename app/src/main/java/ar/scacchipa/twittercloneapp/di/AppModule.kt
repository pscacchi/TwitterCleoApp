package ar.scacchipa.twittercloneapp.di

import ar.scacchipa.twittercloneapp.data.IMapper
import ar.scacchipa.twittercloneapp.data.model.UserAccessTokenData
import ar.scacchipa.twittercloneapp.data.UserAccessTokenDataMapper
import ar.scacchipa.twittercloneapp.domain.model.UserAccessTokenDomain
import ar.scacchipa.twittercloneapp.data.datasource.IAuthDataSource
import ar.scacchipa.twittercloneapp.domain.usecase.AuthorizationUseCase
import ar.scacchipa.twittercloneapp.domain.usecase.ConsumableAuthUseCase
import ar.scacchipa.twittercloneapp.domain.usecase.SplashTimerUseCase
import ar.scacchipa.twittercloneapp.presentation.starter.login.FragmentLoginWebSection
import ar.scacchipa.twittercloneapp.presentation.starter.login.FragmentLogin
import ar.scacchipa.twittercloneapp.presentation.starter.splash.FragmentSplash
import ar.scacchipa.twittercloneapp.data.repository.AuthorizationRepository
import ar.scacchipa.twittercloneapp.data.repository.IAuthorizationRepository
import ar.scacchipa.twittercloneapp.presentation.starter.login.LoginWebSectionViewModel
import ar.scacchipa.twittercloneapp.presentation.starter.login.LoginViewModel
import ar.scacchipa.twittercloneapp.presentation.starter.splash.SplashViewModel
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


    viewModel { LoginWebSectionViewModel( get(), get() ) }
    viewModel { LoginViewModel() }
    viewModel { SplashViewModel( get() ) }

    fragment { FragmentLoginWebSection() }
    fragment { FragmentLogin() }
    fragment { FragmentSplash() }
}