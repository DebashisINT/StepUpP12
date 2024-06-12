package com.ptwelvebreeze.features.leaderboard.api

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import com.fasterxml.jackson.databind.ObjectMapper
import com.ptwelvebreeze.app.FileUtils
import com.ptwelvebreeze.app.Pref
import com.ptwelvebreeze.base.BaseResponse
import com.ptwelvebreeze.features.addshop.model.AddLogReqData
import com.ptwelvebreeze.features.addshop.model.AddShopRequestData
import com.ptwelvebreeze.features.addshop.model.AddShopResponse
import com.ptwelvebreeze.features.addshop.model.LogFileResponse
import com.ptwelvebreeze.features.addshop.model.UpdateAddrReq
import com.ptwelvebreeze.features.contacts.CallHisDtls
import com.ptwelvebreeze.features.contacts.CompanyReqData
import com.ptwelvebreeze.features.contacts.ContactMasterRes
import com.ptwelvebreeze.features.contacts.SourceMasterRes
import com.ptwelvebreeze.features.contacts.StageMasterRes
import com.ptwelvebreeze.features.contacts.StatusMasterRes
import com.ptwelvebreeze.features.contacts.TypeMasterRes
import com.ptwelvebreeze.features.dashboard.presentation.DashboardActivity
import com.ptwelvebreeze.features.login.model.WhatsappApiData
import com.ptwelvebreeze.features.login.model.WhatsappApiFetchData
import com.google.gson.Gson
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * Created by Puja on 10-10-2024.
 */
class LeaderboardRepo(val apiService: LeaderboardApi) {

    fun branchlist(session_token: String): Observable<LeaderboardBranchData> {
        return apiService.branchList(session_token)
    }
    fun ownDatalist(user_id: String,activitybased: String,branchwise: String,flag: String): Observable<LeaderboardOwnData> {
        return apiService.ownDatalist(user_id,activitybased,branchwise,flag)
    }
    fun overAllAPI(user_id: String,activitybased: String,branchwise: String,flag: String): Observable<LeaderboardOverAllData> {
        return apiService.overAllDatalist(user_id,activitybased,branchwise,flag)
    }
}