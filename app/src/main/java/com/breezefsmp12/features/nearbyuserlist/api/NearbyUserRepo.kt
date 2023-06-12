package com.breezefsmp12.features.nearbyuserlist.api

import com.breezefsmp12.app.Pref
import com.breezefsmp12.features.nearbyuserlist.model.NearbyUserResponseModel
import com.breezefsmp12.features.newcollection.model.NewCollectionListResponseModel
import com.breezefsmp12.features.newcollection.newcollectionlistapi.NewCollectionListApi
import io.reactivex.Observable

class NearbyUserRepo(val apiService: NearbyUserApi) {
    fun nearbyUserList(): Observable<NearbyUserResponseModel> {
        return apiService.getNearbyUserList(Pref.session_token!!, Pref.user_id!!)
    }
}