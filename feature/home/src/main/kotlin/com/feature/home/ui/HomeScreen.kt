package com.feature.home.ui

import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PeopleOutline
import androidx.compose.material.icons.rounded.StarRate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.core.designsystem.ShanimeTheme
import com.core.designsystem.component.ErrorIndication
import com.core.designsystem.getSharedTransitionScope
import com.core.model.home.AnimeMetadataModel
import com.feature.home.HomeDestinations
import com.feature.home.R
import com.feature.home.state.HomeUiState
import com.feature.home.viewmodel.HomeViewModel
import java.text.DecimalFormat

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onRetry: () -> Unit,
    navController: NavController,
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
                                onViewDetailClick = { airingAnimeModel ->
                                    navController.navigate(
                                        HomeDestinations.AnimeDetail(
                                            id = airingAnimeModel.malId,
                                            title = airingAnimeModel.title,
                                            image = airingAnimeModel.image,
                                            score = airingAnimeModel.score.toString(),
                                            members = airingAnimeModel.members,
                                            releasedYear = airingAnimeModel.year.toString(),
                                            isAiring = airingAnimeModel.isAiring,
                                            genres = airingAnimeModel.genres.joinToString(separator = ", ") { it.name },
                                            synopsis = airingAnimeModel.synopsis,
                                            trailerVideoId = airingAnimeModel.trailerVideoId,
                                            navigateFromBanner = true,
                                        ),
                                    )
                                },
                            )
                        }
                        item {
                            SectionHeader(
                                title = stringResource(id = R.string.feature_home_top_hit_anime),
                                onSeeAllClick = {},
                            )
                        }
                        item {
                            TopHitAnime(
                                topHitAnime = targetState.topAnime,
                                onClick = { animeModel ->
                                    navController.navigate(
                                        HomeDestinations.AnimeDetail(
                                            id = animeModel.malId,
                                            title = animeModel.title,
                                            image = animeModel.image,
                                            score = animeModel.score,
                                            members = animeModel.members,
                                            releasedYear = animeModel.year,
                                            isAiring = animeModel.isAiring,
                                            genres = animeModel.genres.joinToString(separator = ", ") { it.name },
                                            synopsis = animeModel.synopsis,
                                            trailerVideoId = animeModel.trailerVideoId,
                                            navigateFromBanner = false,
                                        ),
                                    )
                                },
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
                                onClick = { mangaModel ->
                                    navController.navigate(
                                        HomeDestinations.MangaDetail(
                                            id = mangaModel.malId,
                                            title = mangaModel.title,
                                            image = mangaModel.image,
                                            score = mangaModel.score,
                                            members = mangaModel.members,
                                            authorName = mangaModel.authorName,
                                            demographic = mangaModel.demographic,
                                            isOnGoing = mangaModel.isOnGoing,
                                            genres = mangaModel.genres.joinToString(separator = ", ") { it.name },
                                            synopsis = mangaModel.synopsis,
                                        ),
                                    )
                                },
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
    viewModel: HomeViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.homeUiState.collectAsStateWithLifecycle()
    HomeScreen(
        uiState = uiState,
        onRetry = viewModel::onRetry,
        navController = navController,
        modifier = modifier,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ItemContentPreview(
    title: String,
    image: String,
    score: String,
    members: Int,
    genres: List<AnimeMetadataModel>,
    isVisible: Boolean,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.Black.copy(0.5f),
                ),
        ) {
            val formattedMembers = remember {
                DecimalFormat("#,###")
                    .format(members)
            }
            val formattedGenres = remember {
                genres.joinToString(separator = ", ") { it.name }
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .background(
                        color = ShanimeTheme.colors.background,
                        shape = RoundedCornerShape(size = 12.dp),
                    )
                    .padding(all = 20.dp),
            ) {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(150.dp)
                        .aspectRatio(27f / 40f)
                        .sharedElement(
                            state = rememberSharedContentState(key = image),
                            animatedVisibilityScope = this@AnimatedVisibility,
                        )
                        .clip(shape = RoundedCornerShape(size = 12.dp)),
                )
                Spacer(modifier = Modifier.size(20.dp))
                Column(
                    modifier = Modifier
                        .weight(1f),
                ) {
                    Text(
                        text = title,
                        style = ShanimeTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                        color = ShanimeTheme.colors.textPrimary,
                        modifier = Modifier
                            .padding(top = 10.dp),
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.StarRate,
                            contentDescription = null,
                            tint = ShanimeTheme.colors.primary,
                        )
                        Text(
                            text = score,
                            style = ShanimeTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium,
                            ),
                            color = ShanimeTheme.colors.primary,
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        Icon(
                            imageVector = Icons.Rounded.PeopleOutline,
                            contentDescription = null,
                            tint = ShanimeTheme.colors.primary,
                        )
                        Text(
                            text = formattedMembers,
                            style = ShanimeTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium,
                            ),
                            color = ShanimeTheme.colors.primary,
                        )
                    }
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontFamily = ShanimeTheme.typography.bodyMedium.fontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = ShanimeTheme.typography.bodyMedium.fontSize,
                                    color = ShanimeTheme.colors.textPrimary,
                                ),
                            ) {
                                append(text = stringResource(id = R.string.feature_home_genre))
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontFamily = ShanimeTheme.typography.bodySmall.fontFamily,
                                    color = ShanimeTheme.colors.textPrimary,
                                    fontSize = ShanimeTheme.typography.bodyMedium.fontSize,
                                ),
                            ) {
                                append(text = formattedGenres)
                            }
                        },
                        modifier = Modifier
                            .padding(top = 10.dp),
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        shape = RoundedCornerShape(size = 12.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ShanimeTheme.colors.primary,
                            contentColor = Color.White,
                        ),
                        onClick = {},
                        modifier = Modifier
                            .defaultMinSize(minHeight = 1.dp)
                            .align(
                                alignment = Alignment.End,
                            ),
                    ) {
                        Text(
                            text = stringResource(id = R.string.feature_home_view_detail),
                            style = ShanimeTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
private fun ItemContentPreviewPreview() {
    ShanimeTheme {
        SharedTransitionLayout {
            ItemContentPreview(
                title = "Sample Title",
                image = "",
                score = "10",
                members = 10000,
                genres = listOf(),
                isVisible = true,
            )
        }
    }
}
