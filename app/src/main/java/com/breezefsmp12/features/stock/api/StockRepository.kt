package com.breezefsmp12.features.stock.api

import com.breezefsmp12.app.Pref
import com.breezefsmp12.base.BaseResponse
import com.breezefsmp12.features.stock.model.AddStockInputParamsModel
import com.breezefsmp12.features.stock.model.NewStockListResponseModel
import io.reactivex.Observable

/**
 * Created by Saikat on 17-09-2019.
 */
class StockRepository(val apiService: StockApi) {

    fun addStock(addStock: AddStockInputParamsModel): Observable<BaseResponse> {
        return apiService.addStock(addStock)
    }

    fun getStockList(): Observable<NewStockListResponseModel> {
        return apiService.getStockList(Pref.session_token!!, Pref.user_id!!)
    }
}