package com.breezefsmp12.features.location.shopRevisitStatus

import com.breezefsmp12.base.BaseResponse
import com.breezefsmp12.features.location.model.ShopDurationRequest
import com.breezefsmp12.features.location.model.ShopRevisitStatusRequest
import io.reactivex.Observable

class ShopRevisitStatusRepository(val apiService : ShopRevisitStatusApi) {
    fun shopRevisitStatus(shopRevisitStatus: ShopRevisitStatusRequest?): Observable<BaseResponse> {
        return apiService.submShopRevisitStatus(shopRevisitStatus)
    }
}