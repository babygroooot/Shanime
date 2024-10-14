package com.core.domain.home

import com.core.common.formatDate
import com.core.data.home.HomeRepository
import com.core.model.home.ImageType
import com.core.model.home.UserCommentModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMangaUserCommentPreviewUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
) {

    operator fun invoke(
        id: Long,
        isPreliminary: Boolean,
        isSpoiler: Boolean,
    ) = homeRepository.getMangaUserComments(
        id = id,
        isPreliminary = isPreliminary,
        isSpoiler = isSpoiler,
    ).map { result ->
        if (result.isSuccess) {
            val data = result.getOrThrow()
            Result.success(
                data.data.map { userCommentDTO ->
                    UserCommentModel(
                        id = userCommentDTO.malId,
                        userProfile = userCommentDTO.user.images[ImageType.JPG.value]?.imageURL.orEmpty(),
                        username = userCommentDTO.user.username,
                        comment = userCommentDTO.review,
                        score = userCommentDTO.score,
                        reactionCount = userCommentDTO.reactions.overall,
                        date = userCommentDTO.date.formatDate(outputPattern = "dd MMM, yyyy"),
                    )
                },
            )
        } else {
            Result.failure(exception = result.exceptionOrNull() ?: Exception())
        }
    }
}
