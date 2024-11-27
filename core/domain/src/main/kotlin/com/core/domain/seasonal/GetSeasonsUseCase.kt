package com.core.domain.seasonal

import com.core.data.seasonal.SeasonalRepository
import com.core.model.seasonal.SeasonModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSeasonsUseCase @Inject constructor(
    private val seasonalRepository: SeasonalRepository,
) {

    operator fun invoke() = seasonalRepository.getSeasons().map { result ->
        result.map { seasonResponseDto ->
            seasonResponseDto.data.map { seasonDto ->
                SeasonModel(
                    year = seasonDto.year,
                    seasons = seasonDto.seasons,
                )
            }
        }
    }
}
