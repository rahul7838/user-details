package com.example.rahul.trackingdemo.home

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.rahul.trackingdemo.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule(HomeActivity::class.java)

    @Before
    fun setUp() {
//        val instrumentation = InstrumentationRegistry.getInstrumentation()
//        rule.launchActivity(Intent(instrumentation.context, HomeActivity::class.java))
        Thread.sleep(1000)
    }

    @Test
    fun userDetailSuccessTest() {
        onView(withId(R.id.user_list_recycler_view_id)).check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun showLoading() {
    }

    @Test
    fun hideLoading() {
    }
}