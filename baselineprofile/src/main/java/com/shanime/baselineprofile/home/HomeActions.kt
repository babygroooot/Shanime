package com.shanime.baselineprofile.home

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import com.shanime.baselineprofile.flingElementLeftRight
import com.shanime.baselineprofile.untilHasChildren
import com.shanime.baselineprofile.waitAndFindObject
import org.junit.Assert.fail

fun MacrobenchmarkScope.waitForHomeContent() {
    val homeLazyColumn = device.waitAndFindObject(
        selector = By.res("home_lazy_column"),
        timeoutMillis = 30_000,
    )
    homeLazyColumn.wait(untilHasChildren(), 5_000)
}

fun MacrobenchmarkScope.viewBannerDetailAndNavigateBack() {
    val viewBannerDetailButton = device.findObject(
        By.res("view_detail_button"),
    )
    viewBannerDetailButton.click()
    device.wait(Until.hasObject(By.res("navigate_back_button")), 5_000)
    device.findObject(By.res("navigate_back_button")).click()
}

fun MacrobenchmarkScope.flingTopAnimeLeftRight() {
    val topAnimeLazyColumn = device.findObject(
        By.res("top_hit_anime_lazy_column"),
    )
    topAnimeLazyColumn.wait(untilHasChildren(), 5_000)
    device.flingElementLeftRight(topAnimeLazyColumn)
}

fun MacrobenchmarkScope.flingTopMangaLeftRight() {
    val topMangaLazyColumn = device.findObject(
        By.res("top_hit_manga_lazy_column"),
    )
    topMangaLazyColumn.wait(untilHasChildren(), 5_000)
    device.flingElementLeftRight(topMangaLazyColumn)
}

fun MacrobenchmarkScope.viewAnimeDetailAndNavigateBack() {
    val topAnimeLazyColumn = device.findObject(
        By.res("top_hit_anime_lazy_column"),
    )
    val topHitAnimeItem = topAnimeLazyColumn.children.find { it.resourceName == "top_hit_anime_item" }
    if (topHitAnimeItem == null) {
        fail("No top hit anime item found")
    }
    topHitAnimeItem?.click()
    device.wait(Until.hasObject(By.res("navigate_back_button")), 5_000)
    device.findObject(By.res("navigate_back_button")).click()
}

fun MacrobenchmarkScope.viewMangaDetailAndNavigateBack() {
    device.wait(Until.hasObject(By.res("top_hit_manga_lazy_column")), 5_000)
    val topMangaLazyColumn = device.findObject(
        By.res("top_hit_manga_lazy_column"),
    )
    val topHitMangaItem = topMangaLazyColumn.children.find { it.resourceName == "top_hit_manga_item" }
    if (topHitMangaItem == null) {
        fail("No top hit manga item found")
    }
    topHitMangaItem?.click()
    device.wait(Until.hasObject(By.res("manga_navigate_back_button")), 5_000)
    device.findObject(By.res("manga_navigate_back_button")).click()
}
