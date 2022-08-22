package ar.scacchipa.twittercloneapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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
    fun onNavToAuthWebShouldStoreFalseAtNavToAuthWeb() {
        subject.onNavToAuthWeb()
        Assert.assertEquals(false, subject.navToFragAuthWeb.value)
    }

    @Test
    fun onClickLoginButtonShouldStoreTrueAtNavToFragAuthWeb() {
        subject.onClickLoginButton()
        Assert.assertEquals(true, subject.navToFragAuthWeb.value)
    }
}
