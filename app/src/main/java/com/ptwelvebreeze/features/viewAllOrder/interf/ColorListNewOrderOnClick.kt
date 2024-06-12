package com.ptwelvebreeze.features.viewAllOrder.interf

import com.ptwelvebreeze.app.domain.NewOrderColorEntity
import com.ptwelvebreeze.app.domain.NewOrderProductEntity

interface ColorListNewOrderOnClick {
    fun productListOnClick(color: NewOrderColorEntity)
}