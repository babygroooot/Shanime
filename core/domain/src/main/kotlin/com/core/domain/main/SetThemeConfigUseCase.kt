package com.core.domain.main

import com.core.data.main.MainRepository
import com.core.model.main.ShanimeThemeConfig
import javax.inject.Inject

class SetThemeConfigUseCase @Inject constructor(
    private val mainRepository: MainRepository,
) {

    suspend operator fun invoke(themeConfig: ShanimeThemeConfig) {
        mainRepository.setThemeConfig(config = themeConfig.config)
    }
}
