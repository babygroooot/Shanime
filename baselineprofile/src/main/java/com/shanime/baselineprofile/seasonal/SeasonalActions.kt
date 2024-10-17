package com.shanime.baselineprofile.seasonal

import android.os.Handler
import android.os.Looper
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import com.shanime.baselineprofile.flingElementDownUp
import org.junit.Assert.fail

fun MacrobenchmarkScope.navigateToSeasonalAndWaitForContent() {
    val handler = Handler(Looper.getMainLooper())
    device.findObject(By.res("SeasonalNav")).click()
    device.wait(Until.gone(By.res("seasonal_skeleton")), 30_000)
    if (device.findObject(By.res("retry_button")) != null) {
        handler.postDelayed({
            device.findObject(By.res("retry_button")).click()
        }, 1_000)
    }
    device.wait(Until.hasObject(By.res("seasonal_item")), 10_000)
}

fun MacrobenchmarkScope.flingThisSeasonUpDownAndNavigateToDetail() {
    val thisSeasonLazyColumn = device.findObject(By.res("this_season_lazy_column"))
    thisSeasonLazyColumn.wait(Until.hasObject(By.res("seasonal_item")), 5_000)
    device.flingElementDownUp(element = thisSeasonLazyColumn)
    val thisSeasonItem = thisSeasonLazyColumn.children.find { it.resourceName == "seasonal_item" }
    if (thisSeasonItem == null) {
        fail("No seasonal item found")
    }
    thisSeasonItem?.click()
    device.wait(Until.hasObject(By.res("seasonal_navigate_back_button")), 5_000)
    device.findObject(By.res("seasonal_navigate_back_button")).click()
}

fun MacrobenchmarkScope.navigateToUpcomingTabAndFlingUpDown() {
    device.wait(Until.hasObject(By.res("seasonal_horizontal_pager")), 5_000)
    val seasonalHorizontalPager = device.findObject(By.res("seasonal_horizontal_pager"))
    seasonalHorizontalPager.setGestureMargin(device.displayWidth / 5)
    seasonalHorizontalPager.swipe(Direction.LEFT, 1f)
    device.wait(Until.gone(By.res("seasonal_skeleton")), 30_000)
    device.wait(Until.hasObject(By.res("upcoming_lazy_column")), 5_000)
    val upcomingLazyColumn = device.findObject(By.res("upcoming_lazy_column"))
    upcomingLazyColumn.wait(Until.hasObject(By.res("seasonal_item")), 5_000)
    device.flingElementDownUp(upcomingLazyColumn)
}

fun MacrobenchmarkScope.navigateToUpcomingDetail() {
    val upcomingLazyColumn = device.findObject(By.res("upcoming_lazy_column"))
    val upcomingSeasonItem = upcomingLazyColumn.children.find { it.resourceName == "seasonal_item" }
    if (upcomingSeasonItem == null) {
        fail("No seasonal item found")
    }
    upcomingSeasonItem?.click()
    device.wait(Until.hasObject(By.res("seasonal_navigate_back_button")), 5_000)
    device.findObject(By.res("seasonal_navigate_back_button")).click()
}
