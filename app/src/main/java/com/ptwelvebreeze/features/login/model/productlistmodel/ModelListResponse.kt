package com.ptwelvebreeze.features.login.model.productlistmodel

import com.ptwelvebreeze.app.domain.ModelEntity
import com.ptwelvebreeze.app.domain.ProductListEntity
import com.ptwelvebreeze.base.BaseResponse

class ModelListResponse: BaseResponse() {
    var model_list: ArrayList<ModelEntity>? = null
}