package com.breezefsmp12.features.location.shopdurationapi

import com.breezefsmp12.app.NetworkConstant
import com.breezefsmp12.base.BaseResponse
import com.breezefsmp12.features.location.model.MeetingDurationInputParams
import com.breezefsmp12.features.location.model.ShopDurationRequest
import com.breezefsmp12.features.location.model.VisitRemarksResponseModel
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by Pratishruti on 28-11-2017.
 */
interface ShopDurationApi {
    @POST("Shopsubmission/ShopVisited")
    fun submitShopDuration(@Body shopDuration: ShopDurationRequest?): Observable<ShopDurationRequest>

    @POST("Shopsubmission/ITCShopVisited")
    fun submitShopDurationITC(@Body shopDuration: ShopDurationRequest?): Observable<ShopDurationRequest>


    @POST("Shopsubmission/MeetingVisited")
    fun submitMeetingDuration(@Body meetingDuration: MeetingDurationInputParams?): Observable<BaseResponse>

    @FormUrlEncoded
    @POST("VisitRemarks/List")
    fun getRemarksList(@Field("session_token") session_token: String, @Field("user_id") user_id: String): Observable<VisitRemarksResponseModel>

    /**
     * Companion object to create the ShopDurationApi
     */
    companion object Factory {
        fun create(): ShopDurationApi {
            val retrofit = Retrofit.Builder()
                    .client(NetworkConstant.setTimeOutNoRetry())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(NetworkConstant.BASE_URL)
                    .build()

            return retrofit.create(ShopDurationApi::class.java)
        }
    }

}