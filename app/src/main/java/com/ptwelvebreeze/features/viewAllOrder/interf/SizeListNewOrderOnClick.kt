package com.ptwelvebreeze.features.viewAllOrder.interf

import com.ptwelvebreeze.app.domain.NewOrderProductEntity
import com.ptwelvebreeze.app.domain.NewOrderSizeEntity

interface SizeListNewOrderOnClick {
    fun sizeListOnClick(size: NewOrderSizeEntity)
}