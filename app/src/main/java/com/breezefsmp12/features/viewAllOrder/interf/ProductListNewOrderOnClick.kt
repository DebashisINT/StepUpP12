package com.breezefsmp12.features.viewAllOrder.interf

import com.breezefsmp12.app.domain.NewOrderGenderEntity
import com.breezefsmp12.app.domain.NewOrderProductEntity

interface ProductListNewOrderOnClick {
    fun productListOnClick(product: NewOrderProductEntity)
}