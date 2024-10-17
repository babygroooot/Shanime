package com.shanime.baselineprofile.setting

import android.os.Handler
import android.os.Looper
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until

fun MacrobenchmarkScope.navigateToSetting() {
    device.findObject(By.res("SettingNav")).click()
}

fun MacrobenchmarkScope.switchTheme() {
    device.wait(Until.hasObject(By.res("theme_switcher")), 5_000)
    val handler = Handler(Looper.getMainLooper())
    val themeSwitcher = device.findObject(By.res("theme_switcher"))
    handler.postDelayed({
        themeSwitcher.click()
    }, 1_000)
    handler.postDelayed({
        themeSwitcher.click()
    }, 2_000)
}
