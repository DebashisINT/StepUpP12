package com.ptwelvebreeze.features.nearbyuserlist.api

import com.ptwelvebreeze.app.Pref
import com.ptwelvebreeze.features.nearbyuserlist.model.NearbyUserResponseModel
import com.ptwelvebreeze.features.newcollection.model.NewCollectionListResponseModel
import com.ptwelvebreeze.features.newcollection.newcollectionlistapi.NewCollectionListApi
import io.reactivex.Observable

class NearbyUserRepo(val apiService: NearbyUserApi) {
    fun nearbyUserList(): Observable<NearbyUserResponseModel> {
        return apiService.getNearbyUserList(Pref.session_token!!, Pref.user_id!!)
    }
}