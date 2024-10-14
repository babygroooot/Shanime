package com.feature.seasonal.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.core.model.home.AiringSeasonalAnimeModel
import com.feature.seasonal.SeasonalDestinations
import kotlinx.coroutines.flow.Flow

@Composable
fun UpcomingScreen(
    upcomingAnime: Flow<PagingData<AiringSeasonalAnimeModel>>,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val items = upcomingAnime.collectAsLazyPagingItems()
    val isInitialLoading by remember {
        derivedStateOf {
            items.loadState.source.refresh is LoadState.Loading
        }
    }
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        if (isInitialLoading) {
            SeasonalSkeleton()
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(count = 2),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.fillMaxSize(),
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
                        score = "N/A",
                        members = item?.members ?: 0,
                        genres = item?.genres.orEmpty(),
                        onClick = {
                            if (item == null) return@SeasonalItem
                            navController.navigate(
                                route = SeasonalDestinations.AnimeDetail(
                                    id = item.malId,
                                    title = item.title,
                                    image = item.image,
                                    score = item.score.toString(),
                                    members = item.members,
                                    releasedYear = item.year.toString(),
                                    isAiring = item.isAiring,
                                    genres = item.genres.joinToString(separator = ", ") { it.name },
                                    synopsis = item.synopsis,
                                    trailerVideoId = item.trailerVideoId,
                                ),
                            )
                        },
                        modifier = Modifier.animateItem(),
                    )
                }
            }
        }
    }
}