package com.breezefsmp12.features.viewAllOrder.interf

import com.breezefsmp12.app.domain.NewOrderGenderEntity
import com.breezefsmp12.features.viewAllOrder.model.ProductOrder

interface ColorListOnCLick {
    fun colorListOnCLick(size_qty_list: ArrayList<ProductOrder>, adpPosition:Int)
}