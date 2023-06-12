package com.breezefsmp12.features.photoReg.present

import com.breezefsmp12.app.domain.ProspectEntity
import com.breezefsmp12.features.photoReg.model.UserListResponseModel

interface DsStatusListner {
    fun getDSInfoOnLick(obj: ProspectEntity)
}