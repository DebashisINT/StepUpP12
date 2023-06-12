package com.breezefsmp12.features.survey.api

import com.breezefsmp12.features.photoReg.api.GetUserListPhotoRegApi
import com.breezefsmp12.features.photoReg.api.GetUserListPhotoRegRepository

object SurveyDataProvider{

    fun provideSurveyQ(): SurveyDataRepository {
        return SurveyDataRepository(SurveyDataApi.create())
    }

    fun provideSurveyQMultiP(): SurveyDataRepository {
        return SurveyDataRepository(SurveyDataApi.createImage())
    }
}