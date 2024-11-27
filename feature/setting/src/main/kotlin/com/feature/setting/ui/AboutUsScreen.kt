package com.feature.setting.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.core.designsystem.ShanimeTheme
import com.core.designsystem.none
import com.feature.setting.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutUsScreen(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        containerColor = ShanimeTheme.colors.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.feature_setting_about_us),
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            Image(
                painter = painterResource(id = com.core.designsystem.R.drawable.image_preview_placeholder),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .size(100.dp)
                    .clip(shape = CircleShape)
                    .align(alignment = Alignment.CenterHorizontally),
            )
            Text(
                text = "I am genuinely running out of idea, so let's just pretend this is the \"About Us\" screen. Thank you :)",
                style = ShanimeTheme.typography.bodyMedium,
                color = ShanimeTheme.colors.textPrimary,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(top = 20.dp)
                    .padding(horizontal = 20.dp),
            )
        }
    }
}

@PreviewLightDark
@Composable
fun AboutUsScreenPreview() {
    ShanimeTheme {
        AboutUsScreen(
            onNavigateUp = {},
        )
    }
}
