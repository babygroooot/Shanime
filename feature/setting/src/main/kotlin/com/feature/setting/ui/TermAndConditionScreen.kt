package com.feature.setting.ui

import android.webkit.WebView
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.core.designsystem.ShanimeTheme
import com.core.designsystem.none
import com.feature.setting.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermAndConditionScreen(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        containerColor = ShanimeTheme.colors.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.feature_setting_term_and_condition),
                        style = ShanimeTheme.typography.navigationTitle,
                        color = ShanimeTheme.colors.textPrimary,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ShanimeTheme.colors.background,
                ),
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateUp,
                        modifier = Modifier
                            .testTag(tag = "seasonal_navigate_back_button"),
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = null,
                            tint = ShanimeTheme.colors.textPrimary,
                        )
                    }
                },
            )
        },
        contentWindowInsets = WindowInsets.none,
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        AndroidView(
            factory = { context ->
                WebView(context)
            },
            update = { webView ->
                webView.loadUrl("https://www.termsfeed.com/live/e7137865-290f-4689-9a1a-f0ac71484d8b")
            },
            modifier = Modifier.padding(innerPadding),
        )
    }
}
