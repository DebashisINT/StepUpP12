package com.breezefsmp12.features.login.model.productlistmodel

import com.breezefsmp12.app.domain.NewOrderScrOrderEntity
import com.breezefsmp12.app.domain.ProductListEntity

class NewOdrScrOrderListModel {
    var status:String? = null
    var message:String? = null
    var user_id:String? = null
    var order_list: ArrayList<NewOrderScrOrderEntity>? = null
}