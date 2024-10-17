package com.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.core.designsystem.ShanimeTheme

@Composable
fun ErrorIndication(
    errorCaption: String,
    retryText: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .background(
                color = ShanimeTheme.colors.background,
            ),
    ) {
        val lottieComposition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(
                resId = com.core.designsystem.R.raw.error,
            ),
        )
        LottieAnimation(
            composition = lottieComposition,
            iterations = 3,
            modifier = Modifier
                .fillMaxWidth(fraction = 0.7f)
                .aspectRatio(ratio = 1f),
        )
        Text(
            text = errorCaption,
            style = ShanimeTheme.typography.titleSmall.copy(
                lineHeight = 25.sp,
            ),
            color = ShanimeTheme.colors.textPrimary,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = ShanimeTheme.colors.primary,
            ),
            shape = RoundedCornerShape(size = 12.dp),
            modifier = Modifier.testTag(tag = "retry_button"),
        ) {
            Text(
                text = retryText,
                style = ShanimeTheme.typography.titleSmall,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun ErrorIndicationPreview() {
    ShanimeTheme {
        ErrorIndication(
            errorCaption = "Looks like something went wrong.\nPlease try again.",
            retryText = "Try again",
            onRetry = {},
            modifier = Modifier.fillMaxSize(),
        )
    }
}
