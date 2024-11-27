package com.feature.home.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.transformations
import com.core.designsystem.BlurTransformation
import com.core.designsystem.ShanimeTheme
import com.core.designsystem.getNavAnimatedVisibilityScope
import com.core.designsystem.getSharedTransitionScope
import com.core.designsystem.indication.AlphaIndication
import com.core.designsystem.indication.ScaleIndication
import com.core.model.home.AiringSeasonalAnimeModel
import com.core.model.home.AnimeMetadataModel
import com.core.model.home.TopAnimeModel
import com.core.model.home.TopMangaModel
import com.feature.home.HomeSharedElementKey
import com.feature.home.HomeSharedElementType
import com.feature.home.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun BannerPager(
    banners: List<AiringSeasonalAnimeModel>,
    onViewDetailClick: (model: AiringSeasonalAnimeModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pageSize = remember { Short.MAX_VALUE.toInt() }
    val actualPageSize = remember { banners.size }
    val middlePage = remember { pageSize / 2 }
    val bannerPagerState = rememberPagerState(
        pageCount = { pageSize },
        initialPage = middlePage - (middlePage % actualPageSize),
    )
    val cornerSize = with(LocalDensity.current) { 8.dp.toPx() }
    val isDraggingState by bannerPagerState.interactionSource.collectIsDraggedAsState()
    val sharedTransitionScope = getSharedTransitionScope()
    val animatedVisibilityScope = getNavAnimatedVisibilityScope()
    with(sharedTransitionScope) {
        Box(
            modifier = modifier.fillMaxWidth(),
        ) {
            HorizontalPager(
                state = bannerPagerState,
                key = { index ->
                    val actualIndex = index % actualPageSize
                    banners[actualIndex].malId
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(tag = "home_banner"),
            ) { index ->
                val actualIndex = index % actualPageSize
                HomeBannerItem(
                    id = banners[actualIndex].malId,
                    image = banners[actualIndex].image,
                    title = banners[actualIndex].title,
                    genres = banners[actualIndex].genres,
                    onViewDetailClick = { onViewDetailClick(banners[actualIndex]) },
                )
            }
            with(animatedVisibilityScope) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .align(BottomCenter)
                        .padding(bottom = 10.dp)
                        .renderInSharedTransitionScopeOverlay(
                            zIndexInOverlay = 1f,
                        )
                        .animateEnterExit(
                            enter = fadeIn(tween(easing = EaseIn)),
                            exit = fadeOut(tween(easing = EaseOut)),
                        ),
                ) {
                    repeat(actualPageSize) { iteration ->
                        val unselectedColor = Color.White
                        val selectedColor = ShanimeTheme.colors.primary
                        val indicatorColor by animateColorAsState(
                            targetValue = if ((bannerPagerState.currentPage % actualPageSize) == iteration) selectedColor else unselectedColor,
                            label = "Indicator color",
                        )
                        Box(
                            modifier = Modifier
                                .size(height = 4.dp, width = 9.dp)
                                .drawWithCache {
                                    onDrawWithContent {
                                        drawRoundRect(
                                            color = indicatorColor,
                                            cornerRadius = CornerRadius(
                                                x = cornerSize,
                                                y = cornerSize,
                                            ),
                                        )
                                        drawContent()
                                    }
                                },
                        )
                    }
                }
            }
            LaunchedEffect(isDraggingState) {
                snapshotFlow { isDraggingState }.collect { isDragging ->
                    if (isDragging.not()) {
                        while (true) {
                            delay(timeMillis = 3_000)
                            runCatching {
                                bannerPagerState.animateScrollToPage(
                                    page = bannerPagerState.currentPage.inc() % bannerPagerState.pageCount,
                                    animationSpec = tween(),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun HomeBannerItem(
    id: Long,
    image: String,
    title: String,
    genres: List<AnimeMetadataModel>,
    onViewDetailClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val sharedTransitionScope = getSharedTransitionScope()
    val animatedVisibilityScope = getNavAnimatedVisibilityScope()
    with(sharedTransitionScope) {
        Box(modifier = modifier) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(image)
                    .transformations(BlurTransformation())
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(ratio = 1.2f)
                    .sharedElement(
                        state = rememberSharedContentState(
                            key = HomeSharedElementKey(
                                id = id,
                                content = image,
                                type = HomeSharedElementType.BANNER_BACKGROUND,
                            ),
                        ),
                        animatedVisibilityScope = animatedVisibilityScope,
                    ),
            )
            AsyncImage(
                model = image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(ratio = 1.2f)
                    .sharedElement(
                        state = rememberSharedContentState(
                            key = HomeSharedElementKey(
                                id = id,
                                content = image,
                                type = HomeSharedElementType.BANNER_IMAGE,
                            ),
                        ),
                        animatedVisibilityScope = animatedVisibilityScope,
                    ),
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth(0.5f)
                    .padding(start = 20.dp, bottom = 30.dp),
            ) {
                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = ShanimeTheme.typography.navigationTitle.copy(
                        color = Color.White,
                    ),
                    modifier = Modifier
                        .sharedElement(
                            state = rememberSharedContentState(
                                key = HomeSharedElementKey(
                                    id = id,
                                    content = title,
                                    type = HomeSharedElementType.TITLE,
                                ),
                            ),
                            animatedVisibilityScope = animatedVisibilityScope,
                        )
                        .skipToLookaheadSize(),
                )
                val formattedGenres = remember {
                    genres.joinToString(separator = ", ") { it.name }
                }
                Text(
                    text = formattedGenres,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = ShanimeTheme.typography.bodyMedium.copy(
                        color = Color.White,
                    ),
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState(
                                key = HomeSharedElementKey(
                                    id = id,
                                    content = formattedGenres,
                                    type = HomeSharedElementType.GENRE,
                                ),
                            ),
                            animatedVisibilityScope = animatedVisibilityScope,
                        ),
                )
                with(animatedVisibilityScope) {
                    Button(
                        onClick = onViewDetailClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ShanimeTheme.colors.primary,
                            contentColor = ShanimeTheme.colors.background,
                        ),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 3.dp),
                        modifier = Modifier
                            .defaultMinSize(minHeight = 1.dp)
                            .renderInSharedTransitionScopeOverlay(
                                zIndexInOverlay = 1f,
                            )
                            .animateEnterExit(
                                enter = fadeIn(tween(easing = EaseIn)),
                                exit = fadeOut(tween(easing = EaseOut)),
                            )
                            .testTag(tag = "view_detail_button"),
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Menu,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp),
                        )
                        Spacer(modifier = Modifier.size(5.dp))
                        Text(
                            text = stringResource(id = R.string.feature_home_view_detail),
                            style = ShanimeTheme.typography.bodySmall.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Medium,
                            ),
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TopHitAnime(
    topHitAnime: List<TopAnimeModel>,
    onClick: (model: TopAnimeModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val sharedTransitionScope = getSharedTransitionScope()
    with(sharedTransitionScope) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            userScrollEnabled = isTransitionActive.not(),
            modifier = modifier
                .fillMaxWidth()
                .testTag(tag = "top_hit_anime_lazy_column"),
        ) {
            itemsIndexed(
                items = topHitAnime,
            ) { index, item ->
                HomeItem(
                    id = item.malId,
                    itemOrder = index.inc(),
                    image = item.image,
                    score = item.score,
                    onClick = { onClick(item) },
                    modifier = Modifier
                        .animateItem()
                        .testTag(tag = "top_hit_anime_item"),
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TopHitManga(
    topHitManga: List<TopMangaModel>,
    onClick: (model: TopMangaModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val sharedTransitionScope = getSharedTransitionScope()
    with(sharedTransitionScope) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            userScrollEnabled = isTransitionActive.not(),
            modifier = modifier
                .fillMaxWidth()
                .testTag(tag = "top_hit_manga_lazy_column"),
        ) {
            itemsIndexed(
                items = topHitManga,
            ) { index, item ->
                HomeItem(
                    id = item.malId,
                    itemOrder = index.inc(),
                    image = item.image,
                    score = item.score,
                    onClick = { onClick(item) },
                    modifier = Modifier
                        .animateItem()
                        .testTag(tag = "top_hit_manga_item"),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BannerItemPreview() {
    ShanimeTheme {
        HomeBannerItem(
            id = 0,
            image = "",
            title = "Some random title",
            genres = listOf(),
            onViewDetailClick = {},
        )
    }
}

@Composable
fun SectionHeader(
    title: String,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(all = 20.dp)
            .fillMaxWidth(),
    ) {
        Text(
            text = title,
            color = ShanimeTheme.colors.textPrimary,
            style = ShanimeTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
            ),
        )

        Text(
            text = stringResource(id = R.string.feature_home_see_all),
            style = ShanimeTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                color = ShanimeTheme.colors.primary,
            ),
            modifier = Modifier.clickable(
                onClick = onSeeAllClick,
                interactionSource = null,
                indication = AlphaIndication,
            ),
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeItem(
    id: Long,
    itemOrder: Int,
    image: String,
    score: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val sharedTransitionScope = getSharedTransitionScope()
    val animatedVisibilityScope = getNavAnimatedVisibilityScope()
    with(sharedTransitionScope) {
        Box(
            modifier = modifier
                .width(150.dp)
                .aspectRatio(27f / 40f)
                .clickable(
                    interactionSource = null,
                    indication = ScaleIndication,
                    onClick = onClick,
                ),
        ) {
            AsyncImage(
                model = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
                    .sharedBounds(
                        sharedContentState = rememberSharedContentState(
                            key = HomeSharedElementKey(
                                id = id,
                                content = image,
                                type = HomeSharedElementType.LAYOUT,
                            ),
                        ),
                        animatedVisibilityScope = animatedVisibilityScope,
                        enter = fadeIn(tween(easing = EaseIn)),
                        exit = fadeOut(tween(easing = EaseOut)),
                        resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds(contentScale = ContentScale.FillBounds),
                    )
                    .clip(shape = RoundedCornerShape(size = 12.dp)),
            )
            with(animatedVisibilityScope) {
                Text(
                    text = score,
                    style = ShanimeTheme.typography.bodySmall.copy(
                        color = Color.White,
                    ),
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp)
                        .renderInSharedTransitionScopeOverlay(
                            zIndexInOverlay = 1f,
                        )
                        .animateEnterExit(
                            enter = fadeIn(animationSpec = tween(easing = EaseIn)),
                            exit = fadeOut(animationSpec = tween(easing = EaseOut)),
                        )
                        .background(
                            color = ShanimeTheme.colors.primary,
                            shape = RoundedCornerShape(6.dp),
                        )
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                )
                Text(
                    text = itemOrder.toString(),
                    style = ShanimeTheme.typography.navigationTitle.copy(
                        fontSize = 50.sp,
                        color = Color.White,
                    ),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(bottom = 10.dp, start = 10.dp)
                        .renderInSharedTransitionScopeOverlay(
                            zIndexInOverlay = 1f,
                        )
                        .animateEnterExit(
                            enter = fadeIn(animationSpec = tween(easing = EaseIn)),
                            exit = fadeOut(animationSpec = tween(easing = EaseOut)),
                        ),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SectionHeaderPreview() {
    ShanimeTheme {
        SectionHeader(
            title = "Sample Title",
            onSeeAllClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeItemPreview() {
    ShanimeTheme {
        HomeItem(
            id = 0,
            itemOrder = 1,
            image = "",
            score = "10",
            onClick = {},
        )
    }
}
