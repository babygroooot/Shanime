package com.feature.seasonal.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PeopleAlt
import androidx.compose.material.icons.rounded.StarRate
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil3.compose.AsyncImage
import com.core.designsystem.ShanimeTheme
import com.core.designsystem.component.ErrorIndication
import com.core.designsystem.getNavAnimatedVisibilityScope
import com.core.designsystem.getSharedTransitionScope
import com.core.designsystem.indication.ScaleIndication
import com.core.model.home.AiringSeasonalAnimeModel
import com.core.model.home.AnimeMetadataModel
import com.feature.seasonal.R
import com.feature.seasonal.SeasonalSharedElementKey
import com.feature.seasonal.SeasonalSharedElementType
import kotlinx.coroutines.flow.Flow
import java.text.DecimalFormat

@Composable
fun ThisSeasonScreen(
    airingAnime: Flow<PagingData<AiringSeasonalAnimeModel>>,
    onSeasonalItemClick: (model: AiringSeasonalAnimeModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val items = airingAnime.collectAsLazyPagingItems()
    val isInitialLoading by remember {
        derivedStateOf {
            items.loadState.source.refresh is LoadState.Loading
        }
    }
    val isError by remember {
        derivedStateOf {
            items.loadState.hasError && items.itemCount == 0
        }
    }
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        when {
            isInitialLoading -> {
                SeasonalSkeleton()
            }

            isError -> {
                ErrorIndication(
                    errorCaption = stringResource(id = R.string.feature_seasonal_error_desc),
                    retryText = stringResource(id = R.string.feature_seasonal_try_again),
                    onRetry = {
                        items.retry()
                    },
                    modifier = Modifier.fillMaxSize(),
                )
            }

            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(count = 2),
                    contentPadding = PaddingValues(20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag(tag = "this_season_lazy_column"),
                ) {
                    items(
                        count = items.itemCount,
                        key = items.itemKey { it.malId },
                    ) { index ->
                        val item = items[index]
                        SeasonalItem(
                            id = item?.malId ?: 0,
                            title = item?.title.orEmpty(),
                            image = item?.image.orEmpty(),
                            score = (item?.score ?: 0.0).toString(),
                            members = item?.members ?: 0,
                            genres = item?.genres.orEmpty(),
                            onClick = {
                                if (item == null) return@SeasonalItem
                                onSeasonalItemClick(item)
                            },
                            modifier = Modifier.animateItem(),
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SeasonalItem(
    id: Long,
    title: String,
    image: String,
    score: String,
    members: Int,
    genres: List<AnimeMetadataModel>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val sharedTransitionScope = getSharedTransitionScope()
    val animatedVisibilityScope = getNavAnimatedVisibilityScope()
    with(sharedTransitionScope) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = null,
                    indication = ScaleIndication,
                    onClick = onClick,
                )
                .testTag(tag = "seasonal_item"),
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .aspectRatio(27f / 40f),
            ) {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .matchParentSize()
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState(
                                key = SeasonalSharedElementKey(
                                    id = id,
                                    content = image,
                                    type = SeasonalSharedElementType.BANNER_IMAGE,
                                ),
                            ),
                            animatedVisibilityScope = animatedVisibilityScope,
                            resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                            clipInOverlayDuringTransition = OverlayClip(
                                clipShape = RoundedCornerShape(size = 12.dp),
                            ),
                        )
                        .clip(shape = RoundedCornerShape(12.dp)),
                )
                with(animatedVisibilityScope) {
                    Column(
                        modifier = Modifier
                            .padding(start = 10.dp, top = 10.dp)
                            .renderInSharedTransitionScopeOverlay()
                            .animateEnterExit(
                                enter = fadeIn(tween(easing = EaseIn)),
                                exit = fadeOut(tween(easing = EaseOut)),
                            )
                            .background(
                                color = ShanimeTheme.colors.primary,
                                shape = RoundedCornerShape(6.dp),
                            )
                            .padding(5.dp),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = score,
                                style = ShanimeTheme.typography.bodyMedium.copy(
                                    color = Color.White,
                                ),
                            )
                            Spacer(
                                modifier = Modifier.size(2.dp),
                            )
                            Icon(
                                imageVector = Icons.Rounded.StarRate,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(12.dp),
                            )
                        }

                        val formattedMembers = remember {
                            DecimalFormat("#,###")
                                .format(members)
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = formattedMembers,
                                style = ShanimeTheme.typography.bodySmall.copy(
                                    color = Color.White,
                                ),
                            )
                            Spacer(
                                modifier = Modifier.size(2.dp),
                            )
                            Icon(
                                imageVector = Icons.Rounded.PeopleAlt,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(12.dp),
                            )
                        }
                    }
                }
            }
            val genresInString = remember {
                genres.joinToString(separator = ", ") { it.name }
            }
            Text(
                text = title,
                style = ShanimeTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                maxLines = 2,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                color = ShanimeTheme.colors.textPrimary,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .sharedBounds(
                        sharedContentState = rememberSharedContentState(
                            key = SeasonalSharedElementKey(
                                id = id,
                                content = title,
                                type = SeasonalSharedElementType.TITLE,
                            ),
                        ),
                        animatedVisibilityScope = animatedVisibilityScope,
                        resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                    )
                    .skipToLookaheadSize(),
            )
            Text(
                text = "Genre: $genresInString",
                style = ShanimeTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                maxLines = 2,
                color = ShanimeTheme.colors.textPrimary,
                modifier = Modifier
                    .padding(top = 5.dp)
                    .sharedBounds(
                        sharedContentState = rememberSharedContentState(
                            key = SeasonalSharedElementKey(
                                id = id,
                                content = genresInString,
                                type = SeasonalSharedElementType.GENRE,
                            ),
                        ),
                        animatedVisibilityScope = animatedVisibilityScope,
                    )
                    .skipToLookaheadSize(),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SeasonalItemPreview() {
    ShanimeTheme {
        SeasonalItem(
            id = 0,
            title = "Some title",
            image = "",
            score = "10",
            members = 1000000,
            genres = listOf(),
            onClick = {},
        )
    }
}
