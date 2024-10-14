package com.feature.seasonal.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.core.designsystem.ShanimeTheme
import com.core.designsystem.component.shimmerEffect

@Composable
fun SeasonalSkeleton(
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        userScrollEnabled = false,
        columns = GridCells.Fixed(count = 2),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier,
    ) {
        items(count = 6) {
            SeasonalItemSkeleton()
        }
    }
}

@Composable
fun SeasonalItemSkeleton(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(27f / 40f)
                .shimmerEffect(
                    colors = ShanimeTheme.colors.shimmer,
                ),
        )
        Box(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(fraction = 0.9f)
                .height(14.dp)
                .shimmerEffect(
                    colors = ShanimeTheme.colors.shimmer,
                ),
        )
        Box(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(fraction = 0.7f)
                .height(14.dp)
                .shimmerEffect(
                    colors = ShanimeTheme.colors.shimmer,
                ),
        )
    }
}

@Preview
@Composable
private fun SeasonalSkeletonPreview() {
    ShanimeTheme {
        SeasonalSkeleton()
    }
}
