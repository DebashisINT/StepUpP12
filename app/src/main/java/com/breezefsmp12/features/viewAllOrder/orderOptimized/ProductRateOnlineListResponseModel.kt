package com.breezefsmp12.features.viewAllOrder.orderOptimized

import com.breezefsmp12.app.domain.ProductOnlineRateTempEntity
import com.breezefsmp12.base.BaseResponse
import com.breezefsmp12.features.login.model.productlistmodel.ProductRateDataModel
import java.io.Serializable

class ProductRateOnlineListResponseModel: BaseResponse(), Serializable {
    var product_rate_list: ArrayList<ProductOnlineRateTempEntity>? = null
}