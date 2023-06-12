package com.breezefsmp12.features.newcollectionreport

import com.breezefsmp12.features.photoReg.model.UserListResponseModel

interface PendingCollListner {
    fun getUserInfoOnLick(obj: PendingCollData)
}