package com.core.domain.main

import com.core.data.main.MainRepository
import com.core.model.main.ShanimeLanguage
import javax.inject.Inject

class SetPreferredLanguageUseCase @Inject constructor(
    private val mainRepository: MainRepository,
) {

    suspend operator fun invoke(preferredLanguage: ShanimeLanguage) {
        mainRepository.setPreferredLanguage(config = preferredLanguage.languageTag)
    }
}
