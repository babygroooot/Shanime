package com.feature.setting.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.core.designsystem.ShanimeTheme
import com.core.model.main.ShanimeLanguage
import com.feature.setting.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageBottomSheet(
    sheetState: SheetState,
    selectedLanguage: ShanimeLanguage,
    onLanguageSelected: (language: ShanimeLanguage) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
        sheetState = sheetState,
        containerColor = ShanimeTheme.colors.background,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            LanguageItem(
                title = stringResource(id = R.string.feature_setting_english),
                icon = R.drawable.ic_english,
                isSelected = selectedLanguage == ShanimeLanguage.ENGLISH,
                onSelect = {
                    onLanguageSelected(ShanimeLanguage.ENGLISH)
                },
            )
            LanguageItem(
                title = stringResource(id = R.string.feature_setting_khmer),
                icon = R.drawable.ic_cambodia,
                isSelected = selectedLanguage == ShanimeLanguage.KHMER,
                onSelect = {
                    onLanguageSelected(ShanimeLanguage.KHMER)
                },
            )
        }
    }
}

@Composable
fun LanguageItem(
    title: String,
    icon: Int,
    isSelected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(
                    color = ShanimeTheme.colors.primary,
                ),
                onClick = onSelect,
            )
            .padding(horizontal = 20.dp, vertical = 20.dp),
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = null,
        )
        Text(
            text = title,
            style = ShanimeTheme.typography.titleSmall,
            color = ShanimeTheme.colors.textPrimary,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp),
        )
        if (isSelected) {
            Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = null,
                tint = ShanimeTheme.colors.primary,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
private fun LanguageBottomSheetPreview() {
    ShanimeTheme {
        LanguageBottomSheet(
            sheetState = rememberStandardBottomSheetState(
                initialValue = SheetValue.Expanded,
            ),
            selectedLanguage = ShanimeLanguage.ENGLISH,
            onLanguageSelected = {},
            onDismissRequest = {},
        )
    }
}
