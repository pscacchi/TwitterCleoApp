package ar.scacchipa.twittercloneapp.presentation.starter.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ar.scacchipa.twittercloneapp.presentation.starter.login.LoginViewModel
import ar.scacchipa.twittercloneapp.utils.MainCoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    private val subject: LoginViewModel = LoginViewModel()

    @get:Rule
    var mainCoroutineTestRule = MainCoroutineTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun showErrorShouldStoreTrueAtMustShowErrorMsg() {
        subject.showErrorMsg()
        Assert.assertEquals(true, subject.mustShowErrorMsg.value)
    }

    @Test
    fun onClickMsgButtonShouldStoreTrueAtMustShowErrorMsg() {
        subject.onClickMsgButton()
        Assert.assertEquals(false, subject.mustShowErrorMsg.value)
    }

    @Test
    fun onNavToLoginWebShouldStoreFalseAtNavToLoginWeb() {
        subject.onNavToLoginWebSection()
        Assert.assertEquals(false, subject.navToFragLoginWebSection.value)
    }

    @Test
    fun onClickLoginButtonShouldStoreTrueAtNavToFragLoginWebSection() {
        subject.onClickLoginButton()
        Assert.assertEquals(true, subject.navToFragLoginWebSection.value)
    }
}
