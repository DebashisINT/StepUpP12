package com.breezefsmp12.features.stockCompetetorStock.api

import com.breezefsmp12.base.BaseResponse
import com.breezefsmp12.features.orderList.model.NewOrderListResponseModel
import com.breezefsmp12.features.stockCompetetorStock.ShopAddCompetetorStockRequest
import com.breezefsmp12.features.stockCompetetorStock.model.CompetetorStockGetData
import io.reactivex.Observable

class AddCompStockRepository(val apiService:AddCompStockApi){

    fun addCompStock(shopAddCompetetorStockRequest: ShopAddCompetetorStockRequest): Observable<BaseResponse> {
        return apiService.submShopCompStock(shopAddCompetetorStockRequest)
    }

    fun getCompStockList(sessiontoken: String, user_id: String, date: String): Observable<CompetetorStockGetData> {
        return apiService.getCompStockList(sessiontoken, user_id, date)
    }
}