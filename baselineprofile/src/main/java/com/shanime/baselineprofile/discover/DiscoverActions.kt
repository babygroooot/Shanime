package com.shanime.baselineprofile.discover

import android.os.Handler
import android.os.Looper
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import com.shanime.baselineprofile.flingElementDownUp
import com.shanime.baselineprofile.untilHasChildren
import org.junit.Assert.fail

fun MacrobenchmarkScope.navigateToDiscoverAndWaitForContent() {
    device.findObject(By.res("DiscoverNav")).click()
    device.wait(Until.gone(By.res("discover_skeleton")), 30_000)
    val handler = Handler(Looper.getMainLooper())
    if (device.findObject(By.res("retry_button")) != null) {
        handler.postDelayed({
            device.findObject(By.res("retry_button")).click()
        }, 1_000)
    }
    device.wait(Until.hasObject(By.res("discover_item")), 10_000)
}

fun MacrobenchmarkScope.flingDiscoverItemDownUpAndNavigateToDetail() {
    val discoverLazyColumn = device.findObject(By.res("discover_lazy_column"))
    discoverLazyColumn.wait(untilHasChildren(), 5_000)
    device.flingElementDownUp(element = discoverLazyColumn)
    val discoverItem = discoverLazyColumn.children.find { it.resourceName == "discover_item" }
    if (discoverItem == null) {
        fail("No discover item found")
    }
    discoverItem?.click()
    device.wait(Until.hasObject(By.res("discover_navigate_back_button")), 5_000)
    device.findObject(By.res("discover_navigate_back_button")).click()
}
