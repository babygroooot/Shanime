package com.feature.discover.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.core.designsystem.ShanimeTheme

@Composable
fun DiscoverSkeleton(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .padding(vertical = 20.dp)
            .fillMaxWidth()
            .wrapContentHeight(
                align = Alignment.Top,
                unbounded = true,
            ),
    ) {
        repeat(4) {
            DiscoverItemSkeleton()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DiscoverSkeletonPreview() {
    ShanimeTheme {
        DiscoverSkeleton()
    }
}
