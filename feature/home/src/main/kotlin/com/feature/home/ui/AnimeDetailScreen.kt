package com.feature.home.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.core.designsystem.ShanimeTheme
import com.core.designsystem.getNavAnimatedVisibilityScope
import com.core.designsystem.getSharedTransitionScope
import com.core.model.home.UserCommentModel
import com.feature.home.HomeSharedElementKey
import com.feature.home.HomeSharedElementType
import com.feature.home.R
import com.feature.home.state.AnimeDetailUiState
import com.feature.home.viewmodel.AnimeDetailViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun AnimeDetailScreen(
    id: Long,
    title: String,
    image: String,
    score: String,
    members: Int,
    releasedYear: String,
    isAiring: Boolean,
    genres: String,
    synopsis: String,
    trailerVideoId: String,
    uiState: AnimeDetailUiState,
    navigateFromBanner: Boolean,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val lazyListState = rememberLazyListState()
    var bannerIntSize by remember {
        mutableStateOf(IntSize.Zero)
    }
    val initialTitleVerticalOffset = with(LocalDensity.current) {
        20.dp.toPx()
    }
    val scrollValue by remember {
        derivedStateOf {
            if (lazyListState.firstVisibleItemIndex == 0) {
                lazyListState.firstVisibleItemScrollOffset
            } else {
                bannerIntSize.height
            }
        }
    }
    val bannerCollapsePercentage by remember {
        derivedStateOf {
            (
                scrollValue.coerceAtMost(bannerIntSize.height)
                    .toFloat() / bannerIntSize.height
                ).coerceIn(0f..1f)
        }
    }
    val colorBackground = ShanimeTheme.colors.background
    val sharedTransitionScope = getSharedTransitionScope()
    val animatedVisibilityScope = getNavAnimatedVisibilityScope()
    with(sharedTransitionScope) {
        val rootModifier = if (navigateFromBanner) {
            modifier
        } else {
            modifier
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
                    resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds(contentScale = ContentScale.Inside),
                )
        }
        Scaffold(
            containerColor = ShanimeTheme.colors.background,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = title,
                            style = ShanimeTheme.typography.navigationTitle,
                            color = ShanimeTheme.colors.textPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .graphicsLayer {
                                    val titleVerticalScrollFraction =
                                        (scrollValue.toFloat() - (bannerIntSize.height * 0.9f)).coerceAtLeast(
                                            minimumValue = 0f,
                                        )
                                    val titleScrollPercentage =
                                        (titleVerticalScrollFraction / (bannerIntSize.height * 0.1f)).coerceIn(
                                            0f..1f,
                                        )
                                    alpha = titleScrollPercentage
                                    translationY =
                                        initialTitleVerticalOffset - (initialTitleVerticalOffset * titleScrollPercentage)
                                }
                                .basicMarquee(),
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.navigateUp()
                            },
                            modifier = Modifier
                                .testTag(tag = "navigate_back_button"),
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBackIosNew,
                                contentDescription = null,
                                tint = ShanimeTheme.colors.textPrimary,
                            )
                        }
                    },
                    windowInsets = WindowInsets.statusBars,
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent,
                    ),
                    modifier = Modifier
                        .drawWithCache {
                            onDrawBehind {
                                drawRect(
                                    color = colorBackground,
                                    alpha = bannerCollapsePercentage,
                                )
                            }
                        },
                )
            },
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            modifier = rootModifier,
        ) { innerPadding ->
            LazyColumn(
                state = lazyListState,
                contentPadding = WindowInsets.navigationBars.asPaddingValues(),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = innerPadding.calculateBottomPadding()),
            ) {
                item {
                    AnimeBannerItem(
                        id = id,
                        image = image,
                        navigateFromBanner = navigateFromBanner,
                        onSizeChanged = { intSize ->
                            bannerIntSize = intSize
                        },
                    )
                }
                item {
                    AnimeMiddleContentItem(
                        id = id,
                        title = title,
                        score = score,
                        members = members,
                        releasedYear = releasedYear,
                        isAiring = isAiring,
                        genres = genres,
                        synopsis = synopsis,
                        trailerVideoId = trailerVideoId,
                        navigateFromBanner = navigateFromBanner,
                    )
                }
                item {
                    Text(
                        text = stringResource(id = R.string.feature_home_comment),
                        style = ShanimeTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                        color = ShanimeTheme.colors.textPrimary,
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .padding(top = 20.dp),
                    )
                }
                if (uiState is AnimeDetailUiState.Success) {
                    if (uiState.commentList.isNotEmpty()) {
                        items(
                            items = uiState.commentList,
                            key = { item -> item.id },
                        ) { item ->
                            UserCommentItem(
                                userProfile = item.userProfile,
                                username = item.username,
                                comment = item.comment,
                                score = item.score,
                                reactionCount = item.reactionCount,
                                date = item.date,
                                modifier = Modifier.animateItem(),
                            )
                        }
                    } else {
                        item {
                            NoCommentItem()
                        }
                    }
                } else {
                    items(count = 3) {
                        UserCommentSkeleton()
                    }
                }
            }
        }
    }
}

@Composable
fun AnimeDetailScreen(
    id: Long,
    title: String,
    image: String,
    score: String,
    members: Int,
    releasedYear: String,
    isAiring: Boolean,
    genres: String,
    synopsis: String,
    trailerVideoId: String,
    navigateFromBanner: Boolean,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AnimeDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.animeDetailUiState.collectAsStateWithLifecycle()
    AnimeDetailScreen(
        id = id,
        title = title,
        image = image,
        score = score,
        members = members,
        releasedYear = releasedYear,
        isAiring = isAiring,
        genres = genres,
        synopsis = synopsis,
        trailerVideoId = trailerVideoId,
        uiState = uiState,
        navigateFromBanner = navigateFromBanner,
        navController = navController,
        modifier = modifier,
    )
}

@PreviewLightDark
@Composable
private fun AnimeDetailScreenPreview() {
    ShanimeTheme {
        AnimeDetailScreen(
            id = 0,
            title = "Sample title",
            image = "",
            score = "10",
            members = 100000,
            releasedYear = "2024",
            isAiring = true,
            genres = "",
            synopsis = "Barely surviving in a barrel after passing through a terrible whirlpool at sea, carefree Monkey D. Luffy ends up aboard a ship under attack by fearsome pirates. Despite being a naive-looking teenager, he is not to be underestimated. Unmatched in battle, Luffy is a pirate himself who resolutely pursues the coveted One Piece treasure and the King of the Pirates title that comes with it.\\n\\nThe late King of the Pirates, Gol D. Roger, stirred up the world before his death by disclosing the whereabouts of his hoard of riches and daring everyone to obtain it. Ever since then, countless powerful pirates have sailed dangerous seas for the prized One Piece only to never return. Although Luffy lacks a crew and a proper ship, he is endowed with a superhuman ability and an unbreakable spirit that make him not only a formidable adversary but also an inspiration to many.\\n\\nAs he faces numerous challenges with a big smile on his face, Luffy gathers one-of-a-kind companions to join him in his ambitious endeavor, together embracing perils and wonders on their once-in-a-lifetime adventure.\\n\\n[Written by MAL Rewrite]",
            trailerVideoId = "",
            uiState = AnimeDetailUiState.Success(
                commentList = listOf(
                    UserCommentModel(
                        id = 1,
                        userProfile = "",
                        username = "Some Username",
                        comment = "One Piece is definitely a must-watch anime series if anyone is looking for an amazing series with tons of character development, an intricate and engrossing story, very emotional moments, character companionship, some amazing fight scenes, as well as a lot of comedic moments. This anime has been in the top 10 in Japan for a long time, and for good reason. Right from the get go, the anime introduces you to Monkey D. Luffy, our protagonist of the anime, as well as a few more of his crew that will be sailing the Grand Line with him. Each character that ultimatelyends up joining the Strawhat pirates all have a big dream that they want to follow, and usually, that is the reason as to why they join to crew.  It's amazing to see how everyone's dream is tied together and Oda has woven it almost flawlessly.  Each character also has their strengths and weaknesses which come into play throughout the entire anime.  \\n\\nIf you haven't tried One Piece, I urge you to give this show a try.  If you're not used to its animation, please, don't let that be the sole reason that you don't watch this show.  In time, you'll grow to love the animation, and as the series goes on, the animation just gets better and more crisper than before.",
                        score = 10,
                        reactionCount = 1000,
                        date = "10 Aug, 2023",
                    ),
                    UserCommentModel(
                        id = 1,
                        userProfile = "",
                        username = "Some Username",
                        comment = "One Piece is definitely a must-watch anime series if anyone is looking for an amazing series with tons of character development, an intricate and engrossing story, very emotional moments, character companionship, some amazing fight scenes, as well as a lot of comedic moments. This anime has been in the top 10 in Japan for a long time, and for good reason. Right from the get go, the anime introduces you to Monkey D. Luffy, our protagonist of the anime, as well as a few more of his crew that will be sailing the Grand Line with him. Each character that ultimatelyends up joining the Strawhat pirates all have a big dream that they want to follow, and usually, that is the reason as to why they join to crew.  It's amazing to see how everyone's dream is tied together and Oda has woven it almost flawlessly.  Each character also has their strengths and weaknesses which come into play throughout the entire anime.  \\n\\nIf you haven't tried One Piece, I urge you to give this show a try.  If you're not used to its animation, please, don't let that be the sole reason that you don't watch this show.  In time, you'll grow to love the animation, and as the series goes on, the animation just gets better and more crisper than before.",
                        score = 10,
                        reactionCount = 1000,
                        date = "10 Aug, 2023",
                    ),
                    UserCommentModel(
                        id = 1,
                        userProfile = "",
                        username = "Some Username",
                        comment = "One Piece is definitely a must-watch anime series if anyone is looking for an amazing series with tons of character development, an intricate and engrossing story, very emotional moments, character companionship, some amazing fight scenes, as well as a lot of comedic moments. This anime has been in the top 10 in Japan for a long time, and for good reason. Right from the get go, the anime introduces you to Monkey D. Luffy, our protagonist of the anime, as well as a few more of his crew that will be sailing the Grand Line with him. Each character that ultimatelyends up joining the Strawhat pirates all have a big dream that they want to follow, and usually, that is the reason as to why they join to crew.  It's amazing to see how everyone's dream is tied together and Oda has woven it almost flawlessly.  Each character also has their strengths and weaknesses which come into play throughout the entire anime.  \\n\\nIf you haven't tried One Piece, I urge you to give this show a try.  If you're not used to its animation, please, don't let that be the sole reason that you don't watch this show.  In time, you'll grow to love the animation, and as the series goes on, the animation just gets better and more crisper than before.",
                        score = 10,
                        reactionCount = 1000,
                        date = "10 Aug, 2023",
                    ),
                ),
            ),
            navigateFromBanner = true,
            navController = rememberNavController(),
        )
    }
}
