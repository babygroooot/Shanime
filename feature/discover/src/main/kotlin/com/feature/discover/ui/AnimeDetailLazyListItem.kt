package com.feature.discover.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PeopleOutline
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.material.icons.rounded.StarRate
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.transformations
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.core.designsystem.BlurTransformation
import com.core.designsystem.ShanimeTheme
import com.core.designsystem.component.ExpandableText
import com.core.designsystem.component.shimmerEffect
import com.feature.discover.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import java.text.DecimalFormat

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AnimeBannerItem(
    id: Long,
    image: String,
    onSizeChanged: (IntSize) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    Box(
        modifier = modifier.onSizeChanged { intSize ->
            onSizeChanged(intSize)
        },
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(image)
                .transformations(BlurTransformation())
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio = 1.2f),
        )
        AsyncImage(
            model = image,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio = 1.2f),
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AnimeMiddleContentItem(
    id: Long,
    title: String,
    score: String,
    members: Int,
    releasedYear: String,
    isAiring: Boolean,
    genres: String,
    synopsis: String,
    trailerVideoId: String,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    Column(
        modifier = modifier,
    ) {
        Text(
            text = title,
            style = ShanimeTheme.typography.navigationTitle,
            color = ShanimeTheme.colors.textPrimary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 20.dp,
                    top = 20.dp,
                    end = 20.dp,
                ),
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp),
        ) {
            val formattedMembers = remember {
                DecimalFormat("#,###")
                    .format(members)
            }
            val airingStatus = if (isAiring) {
                stringResource(id = R.string.feature_discover_airing)
            } else {
                stringResource(id = R.string.feature_discover_finished)
            }
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
            Spacer(modifier = Modifier.size(10.dp))
            VerticalDivider(
                color = ShanimeTheme.colors.primary,
                thickness = 2.dp,
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = releasedYear,
                style = ShanimeTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                ),
                color = ShanimeTheme.colors.textPrimary,
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = airingStatus,
                style = ShanimeTheme.typography.bodySmall,
                color = ShanimeTheme.colors.primary,
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = ShanimeTheme.colors.primary,
                        shape = RoundedCornerShape(4.dp),
                    )
                    .padding(4.dp),
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
                    append(text = stringResource(id = R.string.feature_discover_genre))
                }
                withStyle(
                    style = SpanStyle(
                        fontFamily = ShanimeTheme.typography.bodySmall.fontFamily,
                        color = ShanimeTheme.colors.textPrimary,
                        fontSize = ShanimeTheme.typography.bodyMedium.fontSize,
                    ),
                ) {
                    append(text = genres)
                }
            },
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp),
        )
        Text(
            text = stringResource(id = R.string.feature_discover_synopsis),
            style = ShanimeTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
            ),
            color = ShanimeTheme.colors.textPrimary,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp),
        )
        ExpandableText(
            text = synopsis,
            truncateText = stringResource(id = R.string.feature_discover_truncated_text),
            textStyle = ShanimeTheme.typography.bodyMedium.copy(
                color = ShanimeTheme.colors.textPrimary,
            ),
            truncateTextStyle = ShanimeTheme.typography.bodyMedium.copy(
                color = ShanimeTheme.colors.primary,
                fontWeight = FontWeight.Bold,
            ),
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 10.dp)
                .fillMaxWidth()
                .animateContentSize(),
        )
        Text(
            text = stringResource(id = R.string.feature_discover_trailer),
            style = ShanimeTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
            ),
            color = ShanimeTheme.colors.textPrimary,
            modifier = Modifier
                .padding(top = 20.dp)
                .padding(horizontal = 20.dp),
        )
        val lifecycleOwner = LocalLifecycleOwner.current
        val youtubeView = remember {
            YouTubePlayerView(context = context)
        }
        val youTubePlayerListener = remember {
            object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    youTubePlayer.cueVideo(
                        videoId = trailerVideoId,
                        startSeconds = 0f,
                    )
                }
            }
        }
        LaunchedEffect(youtubeView) {
            lifecycleOwner.lifecycle.addObserver(youtubeView)
        }
        AndroidView(
            factory = {
                youtubeView.also {
                    it.enableAutomaticInitialization = false
                    it.initialize(youTubePlayerListener = youTubePlayerListener)
                }
            },
            onRelease = {
                it.removeYouTubePlayerListener(youTubePlayerListener = youTubePlayerListener)
                it.release()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .padding(horizontal = 20.dp),
        )
    }
}

@Composable
fun UserCommentItem(
    userProfile: String,
    username: String,
    comment: String,
    score: Int,
    reactionCount: Int,
    date: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .background(color = ShanimeTheme.colors.background),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 20.dp),
        ) {
            AsyncImage(
                model = userProfile,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(shape = CircleShape),
            )
            Text(
                text = username,
                style = ShanimeTheme.typography.titleSmall,
                color = ShanimeTheme.colors.textPrimary,
                modifier = Modifier.padding(start = 20.dp),
            )
        }
        ExpandableText(
            text = comment,
            truncateText = stringResource(id = R.string.feature_discover_truncated_text),
            textStyle = ShanimeTheme.typography.bodyMedium.copy(
                color = ShanimeTheme.colors.textPrimary,
            ),
            truncateTextStyle = ShanimeTheme.typography.bodyMedium.copy(
                color = ShanimeTheme.colors.primary,
                fontWeight = FontWeight.Bold,
            ),
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 10.dp)
                .fillMaxWidth()
                .animateContentSize(),
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 20.dp),
        ) {
            Icon(
                imageVector = Icons.Rounded.StarBorder,
                contentDescription = null,
                tint = ShanimeTheme.colors.primary,
                modifier = Modifier.size(16.dp),
            )
            Text(
                text = "$score/10",
                style = ShanimeTheme.typography.bodySmall,
                color = ShanimeTheme.colors.textSecondary,
            )
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                text = "$reactionCount Reactions",
                style = ShanimeTheme.typography.bodySmall,
                color = ShanimeTheme.colors.textSecondary,
            )
            Text(
                text = date,
                style = ShanimeTheme.typography.bodySmall,
                color = ShanimeTheme.colors.textSecondary,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 20.dp),
            )
        }
    }
}

@Composable
fun UserCommentSkeleton(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(size = 40.dp)
                    .shimmerEffect(
                        colors = ShanimeTheme.colors.shimmer,
                        shape = CircleShape,
                    ),
            )
            Spacer(modifier = Modifier.size(size = 20.dp))
            Box(
                modifier = Modifier
                    .size(
                        width = 90.dp,
                        height = 14.dp,
                    )
                    .shimmerEffect(
                        colors = ShanimeTheme.colors.shimmer,
                    ),
            )
        }
        repeat(times = 3) {
            Spacer(modifier = Modifier.height(5.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .shimmerEffect(
                        colors = ShanimeTheme.colors.shimmer,
                    ),
            )
        }
        Spacer(modifier = Modifier.height(height = 10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Box(
                modifier = Modifier
                    .size(width = 40.dp, height = 12.dp)
                    .shimmerEffect(
                        colors = ShanimeTheme.colors.shimmer,
                    ),
            )
            Spacer(modifier = Modifier.width(width = 20.dp))
            Box(
                modifier = Modifier
                    .size(width = 70.dp, height = 12.dp)
                    .shimmerEffect(
                        colors = ShanimeTheme.colors.shimmer,
                    ),
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .size(width = 80.dp, height = 12.dp)
                    .shimmerEffect(
                        colors = ShanimeTheme.colors.shimmer,
                    ),
            )
        }
    }
}

@Composable
fun NoCommentItem(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth(),
    ) {
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(resId = com.core.designsystem.R.raw.no_comment),
        )
        LottieAnimation(
            iterations = 3,
            composition = composition,
            modifier = Modifier.fillMaxWidth(fraction = 0.7f),
        )
        Text(
            text = stringResource(id = R.string.feature_discover_no_comment_decs),
            style = ShanimeTheme.typography.titleSmall,
            color = ShanimeTheme.colors.textPrimary,
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@PreviewLightDark
@Composable
private fun UserCommentItemPreview() {
    ShanimeTheme {
        UserCommentItem(
            userProfile = "",
            username = "Some Username",
            comment = "One Piece is definitely a must-watch anime series if anyone is looking for an amazing series with tons of character development, an intricate and engrossing story, very emotional moments, character companionship, some amazing fight scenes, as well as a lot of comedic moments. This anime has been in the top 10 in Japan for a long time, and for good reason. Right from the get go, the anime introduces you to Monkey D. Luffy, our protagonist of the anime, as well as a few more of his crew that will be sailing the Grand Line with him. Each character that ultimatelyends up joining the Strawhat pirates all have a big dream that they want to follow, and usually, that is the reason as to why they join to crew.  It's amazing to see how everyone's dream is tied together and Oda has woven it almost flawlessly.  Each character also has their strengths and weaknesses which come into play throughout the entire anime.  \\n\\nIf you haven't tried One Piece, I urge you to give this show a try.  If you're not used to its animation, please, don't let that be the sole reason that you don't watch this show.  In time, you'll grow to love the animation, and as the series goes on, the animation just gets better and more crisper than before.",
            score = 10,
            reactionCount = 50000,
            date = "Aug 05, 2021",
        )
    }
}

@Preview
@Composable
private fun UserCommentSkeletonPreview() {
    ShanimeTheme {
        UserCommentSkeleton()
    }
}

@Preview
@Composable
private fun NoCommentItemPreview() {
    ShanimeTheme {
        NoCommentItem()
    }
}
