package com.breezefsmp12.features.viewAllOrder.interf

import com.breezefsmp12.app.domain.NewOrderColorEntity
import com.breezefsmp12.app.domain.NewOrderProductEntity

interface ColorListNewOrderOnClick {
    fun productListOnClick(color: NewOrderColorEntity)
}