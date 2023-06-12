package com.breezefsmp12.features.stockAddCurrentStock.api

import com.breezefsmp12.base.BaseResponse
import com.breezefsmp12.features.location.model.ShopRevisitStatusRequest
import com.breezefsmp12.features.location.shopRevisitStatus.ShopRevisitStatusApi
import com.breezefsmp12.features.stockAddCurrentStock.ShopAddCurrentStockRequest
import com.breezefsmp12.features.stockAddCurrentStock.model.CurrentStockGetData
import com.breezefsmp12.features.stockCompetetorStock.model.CompetetorStockGetData
import io.reactivex.Observable

class ShopAddStockRepository (val apiService : ShopAddStockApi){
    fun shopAddStock(shopAddCurrentStockRequest: ShopAddCurrentStockRequest?): Observable<BaseResponse> {
        return apiService.submShopAddStock(shopAddCurrentStockRequest)
    }

    fun getCurrStockList(sessiontoken: String, user_id: String, date: String): Observable<CurrentStockGetData> {
        return apiService.getCurrStockListApi(sessiontoken, user_id, date)
    }

}