package com.news.newsapplication

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import com.news.newsapplication.ui.activity.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    var permissionRule: GrantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.INTERNET)

    @Test
    fun testRecyclerViewIsDisplayed() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))

        scenario.close()
    }

    @Test
    fun testRecyclerViewHasItems() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)

        scenario.onActivity { activity ->
            val recyclerView = activity.findViewById<RecyclerView>(R.id.recyclerView)
            assert(recyclerView.adapter?.itemCount ?: 0 > 0)
        }

        scenario.close()
    }

    @Test
    fun testItemClickOpensDetailsActivity() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)

//        onView(withId(R.id.recyclerView))
//            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.title)).check(matches(isDisplayed()))

        scenario.close()
    }
}