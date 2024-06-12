package com.ptwelvebreeze.features.meetinglist.model

import com.ptwelvebreeze.base.BaseResponse
import com.ptwelvebreeze.features.location.model.MeetingDurationDataModel
import java.io.Serializable

/**
 * Created by Saikat on 21-01-2020.
 */
class MeetingListResponseModel : BaseResponse(), Serializable {
    var meeting_list: ArrayList<MeetingDurationDataModel>? = null
}