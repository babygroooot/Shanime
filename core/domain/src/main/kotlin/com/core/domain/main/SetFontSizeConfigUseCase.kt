package com.core.domain.main

import com.core.data.main.MainRepository
import com.core.model.main.ShanimeFontSizeConfig
import javax.inject.Inject

class SetFontSizeConfigUseCase @Inject constructor(
    private val mainRepository: MainRepository,
) {

    suspend operator fun invoke(fontSizeConfig: ShanimeFontSizeConfig) {
        mainRepository.setFontSizeConfig(config = fontSizeConfig.config)
    }
}
