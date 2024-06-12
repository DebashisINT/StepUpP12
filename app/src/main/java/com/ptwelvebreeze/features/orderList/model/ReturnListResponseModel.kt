package com.ptwelvebreeze.features.orderList.model

import com.ptwelvebreeze.base.BaseResponse


class ReturnListResponseModel: BaseResponse() {
    var return_list: ArrayList<ReturnDataModel>? = null
}