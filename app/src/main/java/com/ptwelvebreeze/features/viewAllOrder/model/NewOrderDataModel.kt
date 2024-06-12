package com.ptwelvebreeze.features.viewAllOrder.model

import com.ptwelvebreeze.app.domain.NewOrderColorEntity
import com.ptwelvebreeze.app.domain.NewOrderGenderEntity
import com.ptwelvebreeze.app.domain.NewOrderProductEntity
import com.ptwelvebreeze.app.domain.NewOrderSizeEntity
import com.ptwelvebreeze.features.stockCompetetorStock.model.CompetetorStockGetDataDtls

class NewOrderDataModel {
    var status:String ? = null
    var message:String ? = null
    var Gender_list :ArrayList<NewOrderGenderEntity>? = null
    var Product_list :ArrayList<NewOrderProductEntity>? = null
    var Color_list :ArrayList<NewOrderColorEntity>? = null
    var size_list :ArrayList<NewOrderSizeEntity>? = null
}

