package com.breezefsmp12.features.viewAllOrder.model

import com.breezefsmp12.app.domain.NewOrderColorEntity
import com.breezefsmp12.app.domain.NewOrderGenderEntity
import com.breezefsmp12.app.domain.NewOrderProductEntity
import com.breezefsmp12.app.domain.NewOrderSizeEntity
import com.breezefsmp12.features.stockCompetetorStock.model.CompetetorStockGetDataDtls

class NewOrderDataModel {
    var status:String ? = null
    var message:String ? = null
    var Gender_list :ArrayList<NewOrderGenderEntity>? = null
    var Product_list :ArrayList<NewOrderProductEntity>? = null
    var Color_list :ArrayList<NewOrderColorEntity>? = null
    var size_list :ArrayList<NewOrderSizeEntity>? = null
}

