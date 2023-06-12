package com.breezefsmp12.features.dashboard.presentation.api.dayStartEnd

import com.breezefsmp12.app.Pref
import com.breezefsmp12.base.BaseResponse
import com.breezefsmp12.features.dashboard.presentation.model.DaystartDayendRequest
import com.breezefsmp12.features.dashboard.presentation.model.StatusDayStartEnd
import com.breezefsmp12.features.stockCompetetorStock.ShopAddCompetetorStockRequest
import com.breezefsmp12.features.stockCompetetorStock.api.AddCompStockApi
import io.reactivex.Observable

class DayStartEndRepository (val apiService: DayStartEndApi){
    fun dayStart(daystartDayendRequest: DaystartDayendRequest): Observable<BaseResponse> {
        return apiService.submitDayStartEnd(daystartDayendRequest)
    }

    fun dayStartEndStatus(date:String): Observable<StatusDayStartEnd> {
        return apiService.statusDayStartEnd(Pref.session_token!!, Pref.user_id!!,date)
    }

    fun daystartendDelete(sessionToken:String,usrID:String,date:String,dayStDel:String,dayEndDel:String): Observable<BaseResponse> {
        return apiService.submitDayStartEndDelApi(sessionToken,usrID,date,dayStDel,dayEndDel)
    }


}