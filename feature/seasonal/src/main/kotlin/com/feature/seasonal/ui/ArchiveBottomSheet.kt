package com.feature.seasonal.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.decapitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.core.designsystem.ShanimeTheme
import com.core.designsystem.component.picker.Picker
import com.core.designsystem.component.picker.rememberPickerState
import com.core.designsystem.indication.ScaleIndication
import com.core.designsystem.none
import com.core.model.seasonal.SeasonModel
import com.feature.seasonal.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchiveBottomSheet(
    sheetState: SheetState,
    seasons: List<SeasonModel>,
    onArchivedSelected: (year: String, season: String) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        containerColor = ShanimeTheme.colors.background,
        dragHandle = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 20.dp, bottom = 10.dp)
                        .size(
                            width = 32.dp,
                            height = 4.dp,
                        )
                        .background(
                            color = Color.DarkGray,
                            shape = RoundedCornerShape(28.dp),
                        ),
                )
                Text(
                    text = stringResource(id = R.string.feature_seasonal_archive),
                    style = ShanimeTheme.typography.navigationTitle,
                    color = ShanimeTheme.colors.textPrimary,
                    modifier = Modifier.padding(bottom = 20.dp),
                )
            }
        },
        contentWindowInsets = { WindowInsets.none },
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .navigationBarsPadding()
                .fillMaxWidth(),
        ) {
            val yearPickerState = rememberPickerState()
            val years by remember {
                derivedStateOf {
                    seasons.map { it.year.toString() }
                }
            }
            val seasonPickerState = rememberPickerState()
            var respectiveSeasons by remember {
                mutableStateOf(seasons.first().seasons.map { it.capitalize(locale = Locale.current) })
            }
            LaunchedEffect(yearPickerState.selectedItem) {
                if (yearPickerState.selectedItem.isNotBlank()) {
                    respectiveSeasons = seasons.filter { season ->
                        season.year.toString() == yearPickerState.selectedItem
                    }.flatMap { seasonModel ->
                        seasonModel.seasons.map { it.capitalize(locale = Locale.current) }
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(weight = 1f),
                ) {
                    Text(
                        text = stringResource(id = R.string.feature_seasonal_year),
                        style = ShanimeTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = ShanimeTheme.colors.textPrimary,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Picker(
                        state = yearPickerState,
                        items = years,
                        visibleItemsCount = 4,
                        dividerColor = ShanimeTheme.colors.primary.copy(alpha = 0.7f),
                        textStyle = ShanimeTheme.typography.titleSmall.copy(color = ShanimeTheme.colors.textPrimary),
                        textModifier = Modifier.padding(vertical = 5.dp),
                        modifier = Modifier.padding(horizontal = 10.dp),
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(weight = 1f),
                ) {
                    Text(
                        text = stringResource(id = R.string.feature_seasonal_season),
                        style = ShanimeTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = ShanimeTheme.colors.textPrimary,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Picker(
                        state = seasonPickerState,
                        items = respectiveSeasons,
                        visibleItemsCount = 4,
                        dividerColor = ShanimeTheme.colors.primary.copy(alpha = 0.7f),
                        textStyle = ShanimeTheme.typography.titleSmall.copy(color = ShanimeTheme.colors.textPrimary),
                        textModifier = Modifier.padding(vertical = 5.dp),
                        modifier = Modifier.padding(horizontal = 10.dp),
                    )
                }
            }
            Button(
                onClick = {
                    onArchivedSelected(
                        yearPickerState.selectedItem,
                        seasonPickerState.selectedItem.decapitalize(
                            locale = Locale.current,
                        ),
                    )
                },
                shape = RoundedCornerShape(size = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ShanimeTheme.colors.primary,
                    contentColor = Color.White,
                ),
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                    .fillMaxWidth(fraction = 0.8f),
            ) {
                Text(
                    text = stringResource(id = R.string.feature_seasonal_view_archive),
                    style = ShanimeTheme.typography.titleSmall,
                )
            }
        }
    }
}

@Composable
fun SeasonHeader(
    year: Int,
    modifier: Modifier = Modifier,
) {
    Text(
        text = year.toString(),
        style = ShanimeTheme.typography.titleMedium,
        color = ShanimeTheme.colors.textPrimary,
        modifier = modifier.fillMaxWidth(),
    )
}

@Composable
fun SeasonItem(
    season: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = null,
                indication = ScaleIndication,
                onClick = onClick,
            )
            .border(
                width = 1.dp,
                color = ShanimeTheme.colors.primary,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(all = 10.dp),
    ) {
        Text(
            text = season,
            style = ShanimeTheme.typography.bodyMedium,
            color = ShanimeTheme.colors.primary,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
private fun ArchiveBottomSheetPreview() {
    ShanimeTheme {
        ArchiveBottomSheet(
            sheetState = rememberStandardBottomSheetState(
                initialValue = SheetValue.Expanded,
            ),
            seasons = listOf(
                SeasonModel(
                    year = 2024,
                    seasons = listOf("winter", "spring", "summer", "fall"),
                ),
                SeasonModel(
                    year = 2024,
                    seasons = listOf("winter", "spring", "summer", "fall"),
                ),
                SeasonModel(
                    year = 2024,
                    seasons = listOf("winter", "spring", "summer", "fall"),
                ),
                SeasonModel(
                    year = 2024,
                    seasons = listOf("winter", "spring", "summer", "fall"),
                ),
                SeasonModel(
                    year = 2024,
                    seasons = listOf("winter", "spring", "summer", "fall"),
                ),
                SeasonModel(
                    year = 2024,
                    seasons = listOf("winter", "spring", "summer", "fall"),
                ),

            ),
            onArchivedSelected = { _, _ -> },
            onDismissRequest = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SeasonHeaderPreview() {
    ShanimeTheme {
        SeasonHeader(year = 2024)
    }
}

@Preview
@Composable
private fun SeasonItemPreview() {
    ShanimeTheme {
        SeasonItem(
            season = "Spring",
            onClick = {},
        )
    }
}
