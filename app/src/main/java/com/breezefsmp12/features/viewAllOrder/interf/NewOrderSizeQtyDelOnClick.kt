package com.breezefsmp12.features.viewAllOrder.interf

import com.breezefsmp12.app.domain.NewOrderGenderEntity
import com.breezefsmp12.features.viewAllOrder.model.ProductOrder
import java.text.FieldPosition

interface NewOrderSizeQtyDelOnClick {
    fun sizeQtySelListOnClick(product_size_qty: ArrayList<ProductOrder>)
    fun sizeQtyListOnClick(product_size_qty: ProductOrder,position: Int)
}