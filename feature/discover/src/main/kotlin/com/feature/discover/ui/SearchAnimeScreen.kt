package com.feature.discover.ui

import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.core.designsystem.ShanimeTheme
import com.core.designsystem.getNavAnimatedVisibilityScope
import com.core.model.home.TopAnimeModel
import com.feature.discover.R
import com.feature.discover.viewmodel.SearchAnimeViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun SearchAnimeScreen(
    searchValue: String,
    searchAnime: Flow<PagingData<TopAnimeModel>>,
    onSearchValueChange: (String) -> Unit,
    onNavigateUp: () -> Unit,
    onDiscoverItemClick: (id: Long, title: String, image: String, score: String, members: Int, releasedYear: String, isAiring: Boolean, genres: String, synopsis: String, trailerVideoId: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = ShanimeTheme.colors.background,
            )
            .statusBarsPadding(),
    ) {
        val animatedVisibilityScope = getNavAnimatedVisibilityScope()
        val searchAnimeItems = searchAnime.collectAsLazyPagingItems()
        val searchLottieComposition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(resId = com.core.designsystem.R.raw.search),
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
                .align(alignment = Alignment.CenterHorizontally),
        ) {
            with(animatedVisibilityScope) {
                IconButton(
                    onClick = onNavigateUp,
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .animateEnterExit(
                            enter = fadeIn() + slideInVertically { -it },
                        ),
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBackIosNew,
                        contentDescription = null,
                        tint = ShanimeTheme.colors.textPrimary,
                    )
                }
                OutlinedTextField(
                    value = searchValue,
                    onValueChange = onSearchValueChange,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = ShanimeTheme.colors.surface,
                        unfocusedContainerColor = ShanimeTheme.colors.surface,
                        unfocusedLeadingIconColor = ShanimeTheme.colors.textSecondary,
                        unfocusedPlaceholderColor = ShanimeTheme.colors.textSecondary,
                        unfocusedTextColor = ShanimeTheme.colors.textPrimary,
                        focusedBorderColor = ShanimeTheme.colors.primary,
                        focusedContainerColor = ShanimeTheme.colors.primary.copy(alpha = 0.1f),
                        focusedLeadingIconColor = ShanimeTheme.colors.primary,
                        focusedPlaceholderColor = ShanimeTheme.colors.primary,
                        focusedTextColor = ShanimeTheme.colors.textPrimary,
                        cursorColor = ShanimeTheme.colors.primary,
                    ),
                    shape = RoundedCornerShape(size = 12.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = null,
                        )
                    },
                    textStyle = ShanimeTheme.typography.titleSmall,
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.feature_discover_search),
                            style = ShanimeTheme.typography.titleSmall,
                        )
                    },
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .fillMaxWidth()
                        .animateEnterExit(
                            enter = fadeIn() + slideInVertically { -it },
                        ),
                )
            }
        }
        LazyColumn(
            contentPadding = PaddingValues(
                bottom = 20.dp + WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding(),
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(top = 20.dp)
                .weight(weight = 1f),
        ) {
            when {
                searchValue.isBlank() -> {
                    item {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillParentMaxSize(),
                        ) {
                            LottieAnimation(
                                composition = searchLottieComposition,
                                iterations = 3,
                                modifier = Modifier
                                    .size(size = 180.dp),
                            )
                            Text(
                                text = stringResource(id = R.string.feature_discover_search_decs),
                                style = ShanimeTheme.typography.titleSmall,
                                color = ShanimeTheme.colors.textPrimary,
                            )
                        }
                    }
                }

                else -> {
                    items(
                        count = searchAnimeItems.itemCount,
                        key = searchAnimeItems.itemKey { it.malId },
                    ) { index ->
                        val item = searchAnimeItems[index]
                        DiscoverItem(
                            title = item?.title.orEmpty(),
                            image = item?.image.orEmpty(),
                            score = item?.score.toString(),
                            releasedYear = item?.year.toString(),
                            season = item?.season?.capitalize(Locale.current).orEmpty(),
                            genres = item?.genres.orEmpty(),
                            onClick = {
                                if (item == null) return@DiscoverItem
                                onDiscoverItemClick(
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
                            modifier = Modifier.animateItem(),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchAnimeScreen(
    onNavigateUp: () -> Unit,
    onDiscoverItemClick: (id: Long, title: String, image: String, score: String, members: Int, releasedYear: String, isAiring: Boolean, genres: String, synopsis: String, trailerVideoId: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchAnimeViewModel = hiltViewModel(),
) {
    SearchAnimeScreen(
        searchAnime = viewModel.searchAnime,
        searchValue = viewModel.searchValue,
        onSearchValueChange = viewModel::onSearchValueChange,
        onNavigateUp = onNavigateUp,
        onDiscoverItemClick = onDiscoverItemClick,
        modifier = modifier,
    )
}

@PreviewLightDark
@Composable
private fun SearchAnimeScreenPreview() {
    ShanimeTheme {
        SearchAnimeScreen(
            searchAnime = flowOf(),
            searchValue = "",
            onSearchValueChange = {},
            onNavigateUp = {},
            onDiscoverItemClick = { _, _, _, _, _, _, _, _, _, _ -> },
        )
    }
}
