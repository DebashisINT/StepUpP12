package com.ptwelvebreeze.features.location.shopRevisitStatus

import com.ptwelvebreeze.base.BaseResponse
import com.ptwelvebreeze.features.location.model.ShopDurationRequest
import com.ptwelvebreeze.features.location.model.ShopRevisitStatusRequest
import io.reactivex.Observable

class ShopRevisitStatusRepository(val apiService : ShopRevisitStatusApi) {
    fun shopRevisitStatus(shopRevisitStatus: ShopRevisitStatusRequest?): Observable<BaseResponse> {
        return apiService.submShopRevisitStatus(shopRevisitStatus)
    }
}