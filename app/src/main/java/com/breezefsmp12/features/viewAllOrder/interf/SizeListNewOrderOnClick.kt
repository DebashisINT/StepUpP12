package com.breezefsmp12.features.viewAllOrder.interf

import com.breezefsmp12.app.domain.NewOrderProductEntity
import com.breezefsmp12.app.domain.NewOrderSizeEntity

interface SizeListNewOrderOnClick {
    fun sizeListOnClick(size: NewOrderSizeEntity)
}