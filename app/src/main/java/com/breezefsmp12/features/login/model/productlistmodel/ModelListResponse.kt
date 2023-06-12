package com.breezefsmp12.features.login.model.productlistmodel

import com.breezefsmp12.app.domain.ModelEntity
import com.breezefsmp12.app.domain.ProductListEntity
import com.breezefsmp12.base.BaseResponse

class ModelListResponse: BaseResponse() {
    var model_list: ArrayList<ModelEntity>? = null
}