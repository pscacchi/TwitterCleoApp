package ar.scacchipa.twittercloneapp.di

import ar.scacchipa.twittercloneapp.datasource.IAuthDataSource
import ar.scacchipa.twittercloneapp.domain.AuthorizationUseCase
import ar.scacchipa.twittercloneapp.domain.ConsumableAuthUseCase
import ar.scacchipa.twittercloneapp.domain.SplashTimerUseCase
import ar.scacchipa.twittercloneapp.fragment.FragmentAuthWebDialog
import ar.scacchipa.twittercloneapp.fragment.FragmentHome
import ar.scacchipa.twittercloneapp.fragment.FragmentLogin
import ar.scacchipa.twittercloneapp.fragment.FragmentSplash
import ar.scacchipa.twittercloneapp.repository.AuthorizationRepository
import ar.scacchipa.twittercloneapp.repository.IAuthorizationRepository
import ar.scacchipa.twittercloneapp.viewmodel.AuthWebDialogViewModel
import ar.scacchipa.twittercloneapp.viewmodel.LoginViewModel
import ar.scacchipa.twittercloneapp.viewmodel.SplashViewModel
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {

    single<Retrofit> { provideRetrofit() }

    single<IAuthDataSource> { provideAuthSourceDateApi( get() ) as IAuthDataSource }
    single<IAuthorizationRepository> { AuthorizationRepository( get() ) as IAuthorizationRepository }

    single<AuthorizationUseCase> { AuthorizationUseCase( get() ) }
    single<ConsumableAuthUseCase> { ConsumableAuthUseCase() }
    single<SplashTimerUseCase> { SplashTimerUseCase() }


    viewModel<AuthWebDialogViewModel>{ AuthWebDialogViewModel( get(), get() ) }
    viewModel<LoginViewModel>{ LoginViewModel() }
    viewModel<SplashViewModel> { SplashViewModel( get() ) }

    fragment<FragmentAuthWebDialog> { FragmentAuthWebDialog() }
    fragment<FragmentHome> { FragmentHome() }
    fragment<FragmentLogin>{ FragmentLogin() }
    fragment<FragmentSplash>{ FragmentSplash() }
}