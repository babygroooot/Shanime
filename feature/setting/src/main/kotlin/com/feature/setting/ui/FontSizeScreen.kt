package com.feature.setting.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.core.designsystem.ShanimeTheme
import com.feature.setting.R
import com.feature.setting.viewmodel.FontSizeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FontSizeScreen(
    fontSizeConfig: Float,
    onFontSizeChange: (Float) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        containerColor = ShanimeTheme.colors.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.feature_setting_font_size),
                        style = ShanimeTheme.typography.navigationTitle,
                        color = ShanimeTheme.colors.textPrimary,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = null,
                            tint = ShanimeTheme.colors.textPrimary,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                ),
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        modifier = modifier
            .fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = ShanimeTheme.colors.surface,
                ),
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.feature_setting_preview),
                        style = ShanimeTheme.typography.navigationTitle,
                        color = ShanimeTheme.colors.textPrimary,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                    )
                    Text(
                        text = stringResource(id = R.string.feature_setting_text_preview_1),
                        style = ShanimeTheme.typography.titleMedium,
                        color = ShanimeTheme.colors.textPrimary,
                    )
                    Text(
                        text = stringResource(id = R.string.feature_setting_text_preview_2),
                        style = ShanimeTheme.typography.titleSmall,
                        color = ShanimeTheme.colors.textPrimary.copy(alpha = 0.8f),
                    )
                    Text(
                        text = stringResource(id = R.string.feature_setting_text_preview_3),
                        style = ShanimeTheme.typography.bodyMedium,
                        color = ShanimeTheme.colors.textPrimary,
                    )
                    Text(
                        text = stringResource(id = R.string.feature_setting_text_preview_4),
                        style = ShanimeTheme.typography.bodySmall,
                        color = ShanimeTheme.colors.textPrimary.copy(alpha = 0.7f),
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp),
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_descrease_font_size),
                    contentDescription = null,
                    tint = ShanimeTheme.colors.textPrimary,
                    modifier = Modifier.size(24.dp),
                )
                Slider(
                    value = fontSizeConfig,
                    onValueChange = onFontSizeChange,
                    steps = 1,
                    valueRange = 0f..100f,
                    colors = SliderDefaults.colors(
                        thumbColor = ShanimeTheme.colors.primary,
                        activeTrackColor = ShanimeTheme.colors.primary,
                        inactiveTrackColor = ShanimeTheme.colors.primary.copy(alpha = 0.2f),
                        activeTickColor = ShanimeTheme.colors.primary,
                        inactiveTickColor = ShanimeTheme.colors.secondary,
                    ),
                    modifier = Modifier.weight(1f),
                )
                Icon(
                    painter = painterResource(R.drawable.ic_increase_font_size),
                    contentDescription = null,
                    tint = ShanimeTheme.colors.textPrimary,
                    modifier = Modifier.size(24.dp),
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 5.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.feature_setting_normal),
                    style = ShanimeTheme.typography.titleSmall,
                    color = ShanimeTheme.colors.textPrimary,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = stringResource(id = R.string.feature_setting_medium),
                    style = ShanimeTheme.typography.titleSmall,
                    color = ShanimeTheme.colors.textPrimary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = stringResource(id = R.string.feature_setting_large),
                    style = ShanimeTheme.typography.titleSmall,
                    color = ShanimeTheme.colors.textPrimary,
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
fun FontSizeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: FontSizeViewModel = hiltViewModel(),
) {
    val fontSizeConfig by viewModel.fontSizeConfig.collectAsStateWithLifecycle()
    FontSizeScreen(
        fontSizeConfig = fontSizeConfig,
        onFontSizeChange = viewModel::onFontSizeChange,
        navController = navController,
        modifier = modifier,
    )
}

@PreviewLightDark
@Composable
private fun FontSizeScreenPreview() {
    ShanimeTheme {
        FontSizeScreen(
            fontSizeConfig = 0f,
            onFontSizeChange = {},
            navController = rememberNavController(),
        )
    }
}
