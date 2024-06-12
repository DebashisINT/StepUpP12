package com.ptwelvebreeze.features.login.model.alarmconfigmodel

import com.ptwelvebreeze.base.BaseResponse

/**
 * Created by Saikat on 19-02-2019.
 */
class AlarmConfigResponseModel : BaseResponse() {
    var alarm_settings_list: ArrayList<AlarmConfigDataModel>? = null
}