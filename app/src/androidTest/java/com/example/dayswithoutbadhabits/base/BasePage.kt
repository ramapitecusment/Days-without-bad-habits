package com.example.dayswithoutbadhabits.base

import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.CoreMatchers

open class BasePage : AbstractUiTest() {

    private fun Int.view() = Espresso.onView(ViewMatchers.withId(this))

    protected fun Int.typeText(value: String): ViewInteraction? {
        val result = view().perform(ViewActions.typeText(value))
        Espresso.closeSoftKeyboard()
        return result
    }

    protected fun Int.checkText(value: String) = view().check(ViewAssertions.matches(ViewMatchers.withText(value)))

    protected fun Int.checkNotVisible() = view().check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isDisplayed())))

    protected fun Int.click() = view().perform(ViewActions.click())

    private fun Int.viewInRecycler(position: Int, viewId: Int) =
        Espresso.onView(RecyclerViewMatcher(this).atPosition(position, viewId))

    private fun ViewInteraction.checkText(value: String) = check(ViewAssertions.matches(ViewMatchers.withText(value)))

    private fun ViewInteraction.click() = perform(ViewActions.click())

    protected fun Int.checkRecyclerText(position: Int, viewId: Int, value: String) =
        viewInRecycler(position, viewId).checkText(value)

    protected fun Int.clickRecyclerItem(position: Int, viewId: Int) =
        viewInRecycler(position, viewId).click()

}