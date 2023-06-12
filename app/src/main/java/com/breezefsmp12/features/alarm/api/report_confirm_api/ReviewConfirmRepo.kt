package com.breezefsmp12.features.alarm.api.report_confirm_api

import com.breezefsmp12.base.BaseResponse
import com.breezefsmp12.features.alarm.model.ReviewConfirmInputModel
import io.reactivex.Observable

/**
 * Created by Saikat on 21-02-2019.
 */
class ReviewConfirmRepo(val apiService: ReviewConfirmApi) {
    fun reviewConfirm(reviewConfirm: ReviewConfirmInputModel): Observable<BaseResponse> {
        return apiService.reviewConfirm(reviewConfirm)
    }
}