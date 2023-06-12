package com.breezefsmp12.features.meetinglist.model

import com.breezefsmp12.base.BaseResponse
import com.breezefsmp12.features.location.model.MeetingDurationDataModel
import java.io.Serializable

/**
 * Created by Saikat on 21-01-2020.
 */
class MeetingListResponseModel : BaseResponse(), Serializable {
    var meeting_list: ArrayList<MeetingDurationDataModel>? = null
}