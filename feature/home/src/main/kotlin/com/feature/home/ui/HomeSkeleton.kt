package com.feature.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.core.designsystem.ShanimeTheme
import com.core.designsystem.component.shimmerEffect

@Composable
fun HomeSkeleton(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top, unbounded = true)
            .testTag(tag = "home_skeleton"),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio = 1.2f)
                .shimmerEffect(
                    colors = ShanimeTheme.colors.shimmer,
                    shape = RectangleShape,
                ),
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
        ) {
            Box(
                modifier = Modifier
                    .size(width = 120.dp, height = 16.dp)
                    .shimmerEffect(
                        colors = ShanimeTheme.colors.shimmer,
                    ),
            )
            Box(
                modifier = Modifier
                    .size(width = 80.dp, height = 16.dp)
                    .shimmerEffect(
                        colors = ShanimeTheme.colors.shimmer,
                    ),
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(start = 20.dp)
                .wrapContentWidth(align = Alignment.Start, unbounded = true),
        ) {
            repeat(3) {
                HomeItemSkeleton()
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
        ) {
            Box(
                modifier = Modifier
                    .size(width = 120.dp, height = 16.dp)
                    .shimmerEffect(
                        colors = ShanimeTheme.colors.shimmer,
                    ),
            )
            Box(
                modifier = Modifier
                    .size(width = 80.dp, height = 16.dp)
                    .shimmerEffect(
                        colors = ShanimeTheme.colors.shimmer,
                    ),
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(start = 20.dp)
                .wrapContentWidth(align = Alignment.Start, unbounded = true),
        ) {
            repeat(3) {
                HomeItemSkeleton()
            }
        }
    }
}

@Composable
fun HomeItemSkeleton(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .width(150.dp)
            .aspectRatio(27f / 40f)
            .shimmerEffect(
                colors = ShanimeTheme.colors.shimmer,
            ),
    )
}

@Preview(showBackground = true)
@Composable
private fun HomeSkeletonPreview() {
    ShanimeTheme {
        HomeSkeleton()
    }
}
