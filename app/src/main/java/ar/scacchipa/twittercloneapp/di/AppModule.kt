package ar.scacchipa.twittercloneapp.di

import android.content.SharedPreferences
import ar.scacchipa.twittercloneapp.data.IMapper
import ar.scacchipa.twittercloneapp.data.TweetWrapperMapper
import ar.scacchipa.twittercloneapp.data.UserAccessTokenMapper
import ar.scacchipa.twittercloneapp.data.UserMapper
import ar.scacchipa.twittercloneapp.data.datasource.*
import ar.scacchipa.twittercloneapp.data.model.UserAccessToken
import ar.scacchipa.twittercloneapp.data.model.UserData
import ar.scacchipa.twittercloneapp.data.model.response.TweetsDataWrapper
import ar.scacchipa.twittercloneapp.data.repository.*
import ar.scacchipa.twittercloneapp.domain.model.Credential
import ar.scacchipa.twittercloneapp.domain.model.TweetCardInfo
import ar.scacchipa.twittercloneapp.domain.model.UserInfo
import ar.scacchipa.twittercloneapp.domain.usecase.AuthorizationUseCase
import ar.scacchipa.twittercloneapp.domain.usecase.FetchFeedUseCase
import ar.scacchipa.twittercloneapp.domain.usecase.RevokeCredentialUseCase
import ar.scacchipa.twittercloneapp.domain.usecase.StarterUseCase
import ar.scacchipa.twittercloneapp.presentation.login.FragmentLogin
import ar.scacchipa.twittercloneapp.presentation.login.FragmentLoginWebSection
import ar.scacchipa.twittercloneapp.presentation.login.LoginViewModel
import ar.scacchipa.twittercloneapp.presentation.login.LoginWebSectionViewModel
import ar.scacchipa.twittercloneapp.presentation.main.MainActivityViewModel
import ar.scacchipa.twittercloneapp.presentation.main.home.FragmentHome
import ar.scacchipa.twittercloneapp.presentation.main.home.HomeViewModel
import ar.scacchipa.twittercloneapp.presentation.main.search.FragmentSearch
import ar.scacchipa.twittercloneapp.presentation.splash.FragmentSplash
import ar.scacchipa.twittercloneapp.presentation.splash.SplashViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    single { provideRetrofit() }

    single { provideCredentialLocalSource( androidApplication() ) as SharedPreferences }
    single { SharedPrefsLocalSource( get() ) as ILocalSource }
    single { provideAuthSourceDataApi( get() ) as IAuthExternalSource }
    single { provideTweetDataApi( get() ) as ITweetExternalSource }
    single { provideLoggedUserDataApi( get() ) as ILoggedUserExternalSource }

    single(named("IUserMapper")) {
        UserMapper() as IMapper<UserData, UserInfo>
    }
    single(named("IUserAccessTokenMapper")) {
        UserAccessTokenMapper() as IMapper<UserAccessToken, Credential>
    }
    single(named("ITweetsWrapperMapper")) {
        TweetWrapperMapper(get(named("IUserMapper"))) as IMapper<TweetsDataWrapper, List<TweetCardInfo>>
    }

    single {
        CredentialRepository(get(), get(), get(named("IUserAccessTokenMapper"))) as ICredentialRepository
    }
    single {
        LoggedUserRepository(get(), get()) as ILoggedUserRepository
    }
    single {
        TweetRepository(get(), get(named("ITweetsWrapperMapper")) ) as ITweetRepository
    }

    single { AuthorizationUseCase(get(),get()) as AuthorizationUseCase }
    single { StarterUseCase(get()) as StarterUseCase }
    single { RevokeCredentialUseCase(get()) as RevokeCredentialUseCase }
    single { FetchFeedUseCase(get(), get(), get()) as FetchFeedUseCase }

    viewModel { SplashViewModel( get() ) }
    viewModel { LoginViewModel() }
    viewModel { LoginWebSectionViewModel( get() ) }
    viewModel { MainActivityViewModel( get() ) }
    viewModel { HomeViewModel( get() ) }

    fragment { FragmentSplash() }
    fragment { FragmentLogin() }
    fragment { FragmentLoginWebSection() }
    fragment { FragmentHome() }
    fragment { FragmentSearch() }
}