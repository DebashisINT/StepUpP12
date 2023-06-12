package com.breezefsmp12.features.addAttendence.api.leavetytpeapi

import com.breezefsmp12.app.NetworkConstant
import com.breezefsmp12.base.BaseResponse
import com.breezefsmp12.features.addAttendence.model.ApprovalLeaveResponseModel
import com.breezefsmp12.features.addAttendence.model.LeaveTypeResponseModel
import com.breezefsmp12.features.leaveapplynew.model.ApprovalRejectReqModel
import com.breezefsmp12.features.leaveapplynew.model.clearAttendanceonRejectReqModelRejectReqModel
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by Saikat on 22-11-2018.
 */
interface LeaveTypeApi {
    @FormUrlEncoded
    @POST("Leave/Types")
    fun getLeaveTypeList(@Field("session_token") session_token: String, @Field("user_id") user_id: String): Observable<LeaveTypeResponseModel>


    @FormUrlEncoded
    @POST("LeaveApproval/UserLeaveList")
    fun getApprovalLeaveList(@Field("session_token") session_token: String, @Field("user_id_leave_applied") user_id: String): Observable<ApprovalLeaveResponseModel>


    @POST("LeaveApproval/UserLeaveForApprovalStatus")
    fun postApprovalRejectclick(@Body ApprovalRejectReqModel: ApprovalRejectReqModel?): Observable<BaseResponse>


    @POST("Attendance/DeleteLeaveAttendance")
    fun clearAttendanceonRejectclick(@Body clearAttendanceonRejectReModel: clearAttendanceonRejectReqModelRejectReqModel?): Observable<BaseResponse>

    /**
     * Companion object to create the GithubApiService
     */
    companion object Factory {
        fun create(): LeaveTypeApi {
            val retrofit = Retrofit.Builder()
                    .client(NetworkConstant.setTimeOut())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(NetworkConstant.BASE_URL)
                    .build()

            return retrofit.create(LeaveTypeApi::class.java)
        }
    }
}