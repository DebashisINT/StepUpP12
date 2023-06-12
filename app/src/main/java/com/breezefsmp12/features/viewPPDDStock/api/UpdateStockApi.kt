package com.breezefsmp12.features.viewPPDDStock.api

import com.breezefsmp12.app.NetworkConstant
import com.breezefsmp12.base.BaseResponse
import com.breezefsmp12.features.viewPPDDStock.model.UpdateStockInputParamsModel
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Saikat on 05-10-2018.
 */
interface UpdateStockApi {

    @POST("StockInfo/UpdateStock")
    fun updateStock(@Body updateStock: UpdateStockInputParamsModel?): Observable<BaseResponse>

    /**
     * Companion object to create the ShopDurationApi
     */
    companion object Factory {
        fun create(): UpdateStockApi {
            val retrofit = Retrofit.Builder()
                    .client(NetworkConstant.setTimeOutNoRetry())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(NetworkConstant.BASE_URL)
                    .build()

            return retrofit.create(UpdateStockApi::class.java)
        }
    }
}