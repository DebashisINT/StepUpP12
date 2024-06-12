package com.ptwelvebreeze.features.photoReg.present

import com.ptwelvebreeze.app.domain.ProspectEntity
import com.ptwelvebreeze.features.photoReg.model.UserListResponseModel

interface DsStatusListner {
    fun getDSInfoOnLick(obj: ProspectEntity)
}