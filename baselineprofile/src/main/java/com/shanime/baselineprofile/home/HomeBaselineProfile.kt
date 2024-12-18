package com.shanime.baselineprofile.home

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test

class HomeBaselineProfile {

    @get:Rule
    val rule = BaselineProfileRule()

    @Test
    fun generate() {
        rule.collect(
            packageName = InstrumentationRegistry.getArguments().getString("targetAppId")
                ?: throw Exception("targetAppId not passed as instrumentation runner arg"),
        ) {
            startActivityAndWait()
            waitForHomeContent()
            viewBannerDetailAndNavigateBack()
            flingTopAnimeLeftRight()
            flingTopMangaLeftRight()
            viewAnimeDetailAndNavigateBack()
            viewMangaDetailAndNavigateBack()
        }
    }
}
