package com.feature.home.ui

import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.core.designsystem.component.ErrorIndication
import com.core.designsystem.getSharedTransitionScope
import com.core.model.home.AiringSeasonalAnimeModel
import com.core.model.home.TopAnimeModel
import com.core.model.home.TopMangaModel
import com.feature.home.R
import com.feature.home.state.HomeUiState
import com.feature.home.viewmodel.HomeViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onRetry: () -> Unit,
    onViewDetailClick: (airingAnimeModel: AiringSeasonalAnimeModel) -> Unit,
    onTopAnimeItemClick: (topAnimeModel: TopAnimeModel) -> Unit,
    onTopMangaItemClick: (topManga: TopMangaModel) -> Unit,
    onViewAllTopAnimeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ReportDrawnWhen { uiState is HomeUiState.Success }
    AnimatedContent(
        targetState = uiState,
        transitionSpec = {
            fadeIn(tween(easing = EaseIn)).togetherWith(fadeOut(tween(easing = EaseOut)))
        },
        label = "Home Animated Content",
        modifier = modifier
            .fillMaxSize(),
    ) { targetState ->
        when (targetState) {
            HomeUiState.Loading -> HomeSkeleton()

            is HomeUiState.Success -> {
                val sharedTransitionScope = getSharedTransitionScope()
                with(sharedTransitionScope) {
                    LazyColumn(
                        contentPadding = PaddingValues(bottom = 20.dp),
                        userScrollEnabled = isTransitionActive.not(),
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag(tag = "home_lazy_column"),
                    ) {
                        item {
                            BannerPager(
                                banners = targetState.banners,
                                onViewDetailClick = onViewDetailClick,
                            )
                        }
                        item {
                            SectionHeader(
                                title = stringResource(id = R.string.feature_home_top_hit_anime),
                                onSeeAllClick = onViewAllTopAnimeClick,
                            )
                        }
                        item {
                            TopHitAnime(
                                topHitAnime = targetState.topAnime,
                                onClick = onTopAnimeItemClick,
                                modifier = Modifier.animateItem(),
                            )
                        }
                        item {
                            SectionHeader(
                                title = stringResource(id = R.string.feature_home_top_hit_manga),
                                onSeeAllClick = {},
                            )
                        }
                        item {
                            TopHitManga(
                                topHitManga = targetState.topManga,
                                onClick = onTopMangaItemClick,
                                modifier = Modifier.animateItem(),
                            )
                        }
                    }
                }
            }

            HomeUiState.Error -> ErrorIndication(
                errorCaption = stringResource(id = R.string.feature_home_error_desc),
                retryText = stringResource(id = R.string.feature_home_try_again),
                onRetry = onRetry,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Composable
fun HomeScreen(
    onViewDetailClick: (airingAnimeModel: AiringSeasonalAnimeModel) -> Unit,
    onTopAnimeItemClick: (topAnimeModel: TopAnimeModel) -> Unit,
    onTopMangaItemClick: (topManga: TopMangaModel) -> Unit,
    onViewAllTopAnimeClick: () -> Unit,
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.homeUiState.collectAsStateWithLifecycle()
    HomeScreen(
        uiState = uiState,
        onRetry = viewModel::onRetry,
        onViewDetailClick = onViewDetailClick,
        onTopAnimeItemClick = onTopAnimeItemClick,
        onTopMangaItemClick = onTopMangaItemClick,
        onViewAllTopAnimeClick = onViewAllTopAnimeClick,
        modifier = modifier,
    )
}
