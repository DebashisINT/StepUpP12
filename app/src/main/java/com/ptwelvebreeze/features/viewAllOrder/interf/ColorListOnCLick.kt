package com.ptwelvebreeze.features.viewAllOrder.interf

import com.ptwelvebreeze.app.domain.NewOrderGenderEntity
import com.ptwelvebreeze.features.viewAllOrder.model.ProductOrder

interface ColorListOnCLick {
    fun colorListOnCLick(size_qty_list: ArrayList<ProductOrder>, adpPosition:Int)
}