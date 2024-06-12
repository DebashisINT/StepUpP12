package com.ptwelvebreeze.features.viewAllOrder.orderOptimized

import com.ptwelvebreeze.app.domain.ProductOnlineRateTempEntity
import com.ptwelvebreeze.base.BaseResponse
import com.ptwelvebreeze.features.login.model.productlistmodel.ProductRateDataModel
import java.io.Serializable

class ProductRateOnlineListResponseModel: BaseResponse(), Serializable {
    var product_rate_list: ArrayList<ProductOnlineRateTempEntity>? = null
}