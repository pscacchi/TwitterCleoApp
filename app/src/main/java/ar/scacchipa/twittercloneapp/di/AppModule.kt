package ar.scacchipa.twittercloneapp.di

import android.content.SharedPreferences
import ar.scacchipa.twittercloneapp.data.IMapper
import ar.scacchipa.twittercloneapp.data.UserAccessTokenMapper
import ar.scacchipa.twittercloneapp.data.datasource.IAuthDataSource
import ar.scacchipa.twittercloneapp.data.datasource.ILocalSource
import ar.scacchipa.twittercloneapp.data.datasource.SharedPrefsLocalSource
import ar.scacchipa.twittercloneapp.data.model.UserAccessToken
import ar.scacchipa.twittercloneapp.data.repository.CredentialRepository
import ar.scacchipa.twittercloneapp.data.repository.ICredentialRepository
import ar.scacchipa.twittercloneapp.domain.model.Credential
import ar.scacchipa.twittercloneapp.domain.usecase.AuthorizationUseCase
import ar.scacchipa.twittercloneapp.domain.usecase.StarterUseCase
import ar.scacchipa.twittercloneapp.presentation.login.FragmentLogin
import ar.scacchipa.twittercloneapp.presentation.login.FragmentLoginWebSection
import ar.scacchipa.twittercloneapp.presentation.login.LoginViewModel
import ar.scacchipa.twittercloneapp.presentation.login.LoginWebSectionViewModel
import ar.scacchipa.twittercloneapp.presentation.main.home.FragmentHome
import ar.scacchipa.twittercloneapp.presentation.main.search.FragmentSearch
import ar.scacchipa.twittercloneapp.presentation.splash.FragmentSplash
import ar.scacchipa.twittercloneapp.presentation.splash.SplashViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { provideRetrofit() }
    single { provideCredentialLocalSource( androidApplication() ) as SharedPreferences }
    single { SharedPrefsLocalSource( get() ) as ILocalSource }
    single { provideAuthSourceDataApi( get() ) as IAuthDataSource }

    single { UserAccessTokenMapper() as IMapper<UserAccessToken, Credential> }

    single { CredentialRepository( get(), get(), get() ) as ICredentialRepository }

    single { AuthorizationUseCase( get() ) as AuthorizationUseCase }
    single { StarterUseCase( get() ) as StarterUseCase }

    viewModel { SplashViewModel( get() ) }
    viewModel { LoginViewModel() }
    viewModel { LoginWebSectionViewModel( get() ) }

    fragment { FragmentSplash() }
    fragment { FragmentLogin() }
    fragment { FragmentLoginWebSection() }
    fragment { FragmentHome() }
    fragment { FragmentSearch() }
}