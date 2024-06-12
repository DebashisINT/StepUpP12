package com.ptwelvebreeze.features.viewAllOrder.interf

import com.ptwelvebreeze.app.domain.NewOrderGenderEntity
import com.ptwelvebreeze.features.viewAllOrder.model.ProductOrder
import java.text.FieldPosition

interface NewOrderSizeQtyDelOnClick {
    fun sizeQtySelListOnClick(product_size_qty: ArrayList<ProductOrder>)
    fun sizeQtyListOnClick(product_size_qty: ProductOrder,position: Int)
}