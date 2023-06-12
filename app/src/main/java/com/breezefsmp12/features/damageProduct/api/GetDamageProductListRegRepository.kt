package com.breezefsmp12.features.damageProduct.api

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import com.breezefsmp12.app.FileUtils
import com.breezefsmp12.base.BaseResponse
import com.breezefsmp12.features.NewQuotation.model.*
import com.breezefsmp12.features.addshop.model.AddShopRequestData
import com.breezefsmp12.features.addshop.model.AddShopResponse
import com.breezefsmp12.features.damageProduct.model.DamageProductResponseModel
import com.breezefsmp12.features.damageProduct.model.delBreakageReq
import com.breezefsmp12.features.damageProduct.model.viewAllBreakageReq
import com.breezefsmp12.features.login.model.userconfig.UserConfigResponseModel
import com.breezefsmp12.features.myjobs.model.WIPImageSubmit
import com.breezefsmp12.features.photoReg.model.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class GetDamageProductListRegRepository(val apiService : GetDamageProductListApi) {

    fun viewBreakage(req: viewAllBreakageReq): Observable<DamageProductResponseModel> {
        return apiService.viewBreakage(req)
    }

    fun delBreakage(req: delBreakageReq): Observable<BaseResponse>{
        return apiService.BreakageDel(req.user_id!!,req.breakage_number!!,req.session_token!!)
    }

}