package com.core.domain.home

import android.R.attr.data
import com.core.common.formatDate
import com.core.data.home.HomeRepository
import com.core.model.home.ImageType
import com.core.model.home.UserCommentModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAnimeUserCommentPreviewUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
) {

    operator fun invoke(
        id: Long,
        isPreliminary: Boolean,
        isSpoiler: Boolean,
    ) = homeRepository.getAnimeUserCommentsPreview(
        id = id,
        isPreliminary = isPreliminary,
        isSpoiler = isSpoiler,
    ).map { result ->
        result.map { response ->
            response.data.map { userCommentDTO ->
                UserCommentModel(
                    id = userCommentDTO.malId,
                    userProfile = userCommentDTO.user.images[ImageType.JPG.value]?.imageURL.orEmpty(),
                    username = userCommentDTO.user.username,
                    comment = userCommentDTO.review,
                    score = userCommentDTO.score,
                    reactionCount = userCommentDTO.reactions.overall,
                    date = userCommentDTO.date.formatDate(outputPattern = "dd MMM, yyyy"),
                )
            }
        }
    }
}
