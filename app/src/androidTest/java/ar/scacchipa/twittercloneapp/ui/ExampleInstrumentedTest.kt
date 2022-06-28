package ar.scacchipa.twittercloneapp.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import ar.scacchipa.twittercloneapp.R
import ar.scacchipa.twittercloneapp.component.TwitterCloneActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class SplashTest {

    @get:Rule
    val activityRule = ActivityTestRule(TwitterCloneActivity::class.java)

    @Test
    fun splashSpendInFiveSeconds() {
        onView(withId(R.id.twitter_logo_imageview))
            .check(matches(isDisplayed()))

        onView(withId(R.layout.fragment_login_layout))
            .check(doesNotExist())

        Thread.sleep(4000)

        onView(withId(R.id.twitter_logo_imageview))
            .check(matches(isDisplayed()))

        Thread.sleep(1000)

        onView(withId(R.id.twitter_logo_imageview))
            .check(doesNotExist())
    }
}