package com.feature.seasonal.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import com.core.designsystem.ShanimeTheme
import com.core.designsystem.component.pagerTabIndicatorOffset
import com.core.designsystem.getNavAnimatedVisibilityScope
import com.core.designsystem.getSharedTransitionScope
import com.core.model.home.AiringSeasonalAnimeModel
import com.core.model.seasonal.SeasonModel
import com.feature.seasonal.R
import com.feature.seasonal.SeasonalTabs
import com.feature.seasonal.viewmodel.SeasonalViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SeasonalScreen(
    airingAnime: Flow<PagingData<AiringSeasonalAnimeModel>>,
    upcomingAnime: Flow<PagingData<AiringSeasonalAnimeModel>>,
    seasons: List<SeasonModel>,
    onSeasonalItemClick: (model: AiringSeasonalAnimeModel) -> Unit,
    onArchivedSelected: (year: String, season: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val sharedTransitionScope = getSharedTransitionScope()
    val animatedVisibilityScope = getNavAnimatedVisibilityScope()
    var showArchiveBottomSheet by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    val archiveSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    with(sharedTransitionScope) {
        with(animatedVisibilityScope) {
            Scaffold(
                containerColor = ShanimeTheme.colors.background,
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = stringResource(R.string.feature_seasonal_seasonal),
                                style = ShanimeTheme.typography.navigationTitle,
                                color = ShanimeTheme.colors.textPrimary,
                            )
                        },
                        actions = {
                            IconButton(
                                onClick = {
                                    showArchiveBottomSheet = true
                                },
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_archive),
                                    contentDescription = null,
                                    tint = ShanimeTheme.colors.textPrimary,
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = ShanimeTheme.colors.background,
                        ),
                        modifier = Modifier
                            .renderInSharedTransitionScopeOverlay(
                                zIndexInOverlay = 1f,
                            )
                            .animateEnterExit(
                                enter = fadeIn(tween(easing = EaseIn)),
                                exit = fadeOut(tween(easing = EaseOut)),
                            ),
                    )
                },
                modifier = modifier.fillMaxSize(),
            ) { innerPadding ->
                val seasonalTabs = remember { SeasonalTabs.entries }
                var selectedTabIndex by remember {
                    mutableIntStateOf(0)
                }
                val pagerState = rememberPagerState { seasonalTabs.size }
                Column(
                    modifier = Modifier
                        .padding(innerPadding),
                ) {
                    TabRow(
                        selectedTabIndex = selectedTabIndex,
                        containerColor = ShanimeTheme.colors.background,
                        contentColor = ShanimeTheme.colors.primary,
                        indicator = { tabPositions ->
                            TabRowDefaults.SecondaryIndicator(
                                modifier = Modifier.pagerTabIndicatorOffset(
                                    pagerState = pagerState,
                                    tabPositions = tabPositions,
                                ),
                                color = ShanimeTheme.colors.primary,
                            )
                        },
                        divider = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .renderInSharedTransitionScopeOverlay(
                                zIndexInOverlay = 1f,
                            )
                            .animateEnterExit(
                                enter = fadeIn(tween(easing = EaseIn)),
                                exit = fadeOut(tween(easing = EaseOut)),
                            ),
                    ) {
                        seasonalTabs.forEachIndexed { index, seasonalTab ->
                            Tab(
                                selected = index == selectedTabIndex,
                                selectedContentColor = ShanimeTheme.colors.primary,
                                unselectedContentColor = ShanimeTheme.colors.textPrimary,
                                onClick = {
                                    scope.launch {
                                        selectedTabIndex = index
                                        pagerState.animateScrollToPage(page = selectedTabIndex)
                                    }
                                },
                                text = {
                                    Text(
                                        text = stringResource(seasonalTab.title),
                                        style = ShanimeTheme.typography.bodyMedium.copy(
                                            color = Color.Unspecified,
                                            fontWeight = FontWeight.Medium,
                                        ),
                                    )
                                },
                            )
                        }
                    }
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .testTag(tag = "seasonal_horizontal_pager"),
                    ) { index ->
                        if (index == 0) {
                            ThisSeasonScreen(
                                airingAnime = airingAnime,
                                onSeasonalItemClick = onSeasonalItemClick,
                            )
                        } else {
                            UpcomingScreen(
                                upcomingAnime = upcomingAnime,
                                onSeasonalItemClick = onSeasonalItemClick,
                            )
                        }
                    }
                    LaunchedEffect(pagerState.currentPage) {
                        selectedTabIndex = pagerState.currentPage
                    }
                }
            }
            if (showArchiveBottomSheet) {
                ArchiveBottomSheet(
                    sheetState = archiveSheetState,
                    seasons = seasons,
                    onArchivedSelected = { year, season ->
                        scope.launch {
                            archiveSheetState.hide()
                        }.invokeOnCompletion {
                            if (archiveSheetState.isVisible.not()) {
                                showArchiveBottomSheet = false
                                onArchivedSelected(year, season)
                            }
                        }
                    },
                    onDismissRequest = {
                        scope.launch {
                            archiveSheetState.hide()
                        }.invokeOnCompletion {
                            if (archiveSheetState.isVisible.not()) {
                                showArchiveBottomSheet = false
                            }
                        }
                    },
                )
            }
        }
    }
}

@Composable
fun SeasonalScreen(
    onSeasonalItemClick: (model: AiringSeasonalAnimeModel) -> Unit,
    onArchivedSelected: (year: String, season: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SeasonalViewModel = hiltViewModel(),
) {
    val seasons by viewModel.seasons.collectAsStateWithLifecycle()
    SeasonalScreen(
        airingAnime = viewModel.airingAnime,
        upcomingAnime = viewModel.upcomingAnime,
        seasons = seasons,
        onSeasonalItemClick = onSeasonalItemClick,
        onArchivedSelected = onArchivedSelected,
        modifier = modifier,
    )
}
