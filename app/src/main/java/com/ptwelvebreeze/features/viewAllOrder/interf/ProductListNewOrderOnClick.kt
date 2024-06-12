package com.ptwelvebreeze.features.viewAllOrder.interf

import com.ptwelvebreeze.app.domain.NewOrderGenderEntity
import com.ptwelvebreeze.app.domain.NewOrderProductEntity

interface ProductListNewOrderOnClick {
    fun productListOnClick(product: NewOrderProductEntity)
}