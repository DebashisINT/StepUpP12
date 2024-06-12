package com.ptwelvebreeze.features.newcollectionreport

import com.ptwelvebreeze.features.photoReg.model.UserListResponseModel

interface PendingCollListner {
    fun getUserInfoOnLick(obj: PendingCollData)
}