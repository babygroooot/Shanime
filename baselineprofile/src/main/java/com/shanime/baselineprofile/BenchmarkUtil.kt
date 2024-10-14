package com.shanime.baselineprofile

import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.UiObject2Condition
import androidx.test.uiautomator.Until

fun untilHasChildren(
    childCount: Int = 1,
    op: HasChildrenOp = HasChildrenOp.AT_LEAST,
): UiObject2Condition<Boolean> = object : UiObject2Condition<Boolean>() {
    override fun apply(element: UiObject2): Boolean = when (op) {
        HasChildrenOp.AT_LEAST -> element.childCount >= childCount
        HasChildrenOp.EXACTLY -> element.childCount == childCount
        HasChildrenOp.AT_MOST -> element.childCount <= childCount
    }
}

enum class HasChildrenOp {
    AT_LEAST,
    EXACTLY,
    AT_MOST,
}

fun UiDevice.waitAndFindObject(selector: BySelector, timeoutMillis: Long): UiObject2 {
    if (!wait(Until.hasObject(selector), timeoutMillis)) {
        throw AssertionError("Element not found on screen in ${timeoutMillis}ms (selector=$selector)")
    }

    return findObject(selector)
}

fun UiDevice.flingElementDownUp(element: UiObject2) {
    // Set some margin from the sides to prevent triggering system navigation
    element.setGestureMargin(displayWidth / 5)

    element.fling(Direction.DOWN)
    waitForIdle()
    element.fling(Direction.UP)
}

fun UiDevice.flingElementLeftRight(element: UiObject2) {
    // Set some margin from the sides to prevent triggering system navigation
    element.setGestureMargin(displayWidth / 5)

    element.fling(Direction.RIGHT)
    waitForIdle()
    element.fling(Direction.LEFT)
}
