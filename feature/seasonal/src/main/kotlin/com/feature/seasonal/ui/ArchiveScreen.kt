package com.feature.seasonal.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil3.compose.AsyncImage
import com.core.designsystem.ShanimeTheme
import com.core.designsystem.component.ErrorIndication
import com.core.designsystem.component.shimmerEffect
import com.core.designsystem.indication.ScaleIndication
import com.core.designsystem.none
import com.core.model.home.AnimeMetadataModel
import com.core.model.home.TopAnimeModel
import com.feature.seasonal.R
import com.feature.seasonal.viewmodel.ArchiveViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchiveScreen(
    archivedAnime: Flow<PagingData<TopAnimeModel>>,
    onArchivedItemClick: (id: Long, title: String, image: String, score: String, members: Int, releasedYear: String, isAiring: Boolean, genres: String, synopsis: String, trailerVideoId: String) -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        containerColor = ShanimeTheme.colors.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.feature_seasonal_archive),
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
        val topAnimeItems = archivedAnime.collectAsLazyPagingItems()
        val isInitialLoading by remember {
            derivedStateOf {
                topAnimeItems.loadState.source.refresh is LoadState.Loading
            }
        }
        val isError by remember {
            derivedStateOf {
                topAnimeItems.loadState.hasError && topAnimeItems.itemCount == 0
            }
        }
        if (isInitialLoading) {
            DiscoverSkeleton(
                modifier = Modifier.padding(innerPadding),
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(
                    top = 20.dp,
                    bottom = 20.dp + WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding(),
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .testTag(tag = "discover_lazy_column"),
            ) {
                if (isError) {
                    item {
                        ErrorIndication(
                            errorCaption = stringResource(id = R.string.feature_seasonal_error_desc),
                            retryText = stringResource(id = R.string.feature_seasonal_try_again),
                            onRetry = {
                                topAnimeItems.retry()
                            },
                            modifier = Modifier.fillParentMaxSize(),
                        )
                    }
                } else {
                    items(
                        count = topAnimeItems.itemCount,
                        key = topAnimeItems.itemKey { it.malId },
                    ) { index ->
                        val item = topAnimeItems[index]
                        if (item == null) {
                            ArchiveItemSkeleton()
                        } else {
                            ArchiveItem(
                                title = item.title,
                                image = item.image,
                                score = item.score,
                                releasedYear = item.year,
                                season = item.season.capitalize(Locale.current),
                                genres = item.genres,
                                onClick = {
                                    onArchivedItemClick(
                                        item.malId,
                                        item.title,
                                        item.image,
                                        item.score,
                                        item.members,
                                        item.year,
                                        item.isAiring,
                                        item.genres.joinToString(separator = ", ") { it.name },
                                        item.synopsis,
                                        item.trailerVideoId,
                                    )
                                },
                                modifier = Modifier
                                    .animateItem()
                                    .testTag(tag = "discover_item"),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ArchiveScreen(
    onArchivedItemClick: (id: Long, title: String, image: String, score: String, members: Int, releasedYear: String, isAiring: Boolean, genres: String, synopsis: String, trailerVideoId: String) -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ArchiveViewModel = hiltViewModel(),
) {
    ArchiveScreen(
        archivedAnime = viewModel.archivedAnime,
        onArchivedItemClick = onArchivedItemClick,
        onNavigateUp = onNavigateUp,
        modifier = modifier,
    )
}

@Composable
fun ArchiveItem(
    title: String,
    image: String,
    score: String,
    releasedYear: String,
    season: String,
    genres: List<AnimeMetadataModel>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = null,
                indication = ScaleIndication,
                onClick = onClick,
            ),
    ) {
        Box(
            modifier = Modifier
                .padding(end = 20.dp)
                .width(150.dp)
                .aspectRatio(27f / 40f),
        ) {
            AsyncImage(
                model = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
                    .clip(shape = RoundedCornerShape(12.dp)),
            )
            Text(
                text = score,
                style = ShanimeTheme.typography.bodySmall.copy(
                    color = Color.White,
                ),
                modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp)
                    .background(
                        color = ShanimeTheme.colors.primary,
                        shape = RoundedCornerShape(6.dp),
                    )
                    .padding(horizontal = 10.dp, vertical = 5.dp),
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.padding(vertical = 20.dp),
        ) {
            val genresInString = genres.joinToString(separator = ", ") { it.name }
            Text(
                text = title,
                style = ShanimeTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                color = ShanimeTheme.colors.textPrimary,
            )
            Text(
                text = "$releasedYear | $season",
                style = ShanimeTheme.typography.titleSmall,
                color = ShanimeTheme.colors.textPrimary,
            )
            Text(
                text = "Genre: $genresInString",
                style = ShanimeTheme.typography.bodyMedium,
                color = ShanimeTheme.colors.textPrimary,
            )
        }
    }
}

@Composable
fun ArchiveItemSkeleton(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
    ) {
        Box(
            modifier = modifier
                .padding(end = 20.dp)
                .width(150.dp)
                .aspectRatio(27f / 40f)
                .shimmerEffect(
                    colors = ShanimeTheme.colors.shimmer,
                ),
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .padding(vertical = 20.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(18.dp)
                    .shimmerEffect(
                        colors = ShanimeTheme.colors.shimmer,
                    ),
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(14.dp)
                    .shimmerEffect(
                        colors = ShanimeTheme.colors.shimmer,
                    ),
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(12.dp)
                    .shimmerEffect(
                        colors = ShanimeTheme.colors.shimmer,
                    ),
            )
        }
    }
}

@Preview
@Composable
private fun ArchiveScreenPreview() {
    ShanimeTheme {
        ArchiveScreen(
            archivedAnime = flowOf(),
            onArchivedItemClick = { _, _, _, _, _, _, _, _, _, _ -> },
            onNavigateUp = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ArchiveItemPreview() {
    ShanimeTheme {
        ArchiveItem(
            title = "Some title",
            image = "",
            score = "10",
            releasedYear = "2021",
            season = "Japan",
            genres = listOf(),
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ArchiveItemSkeletonPreview() {
    ShanimeTheme {
        ArchiveItemSkeleton()
    }
}

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
            )
            .testTag(tag = "discover_skeleton"),
    ) {
        repeat(4) {
            ArchiveItemSkeleton()
        }
    }
}
