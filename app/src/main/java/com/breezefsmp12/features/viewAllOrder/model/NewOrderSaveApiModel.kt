package com.breezefsmp12.features.viewAllOrder.model

import com.breezefsmp12.features.stockCompetetorStock.ShopAddCompetetorStockProductList
import com.breezefsmp12.features.viewAllOrder.orderNew.NeworderScrCartFragment

class NewOrderSaveApiModel {
    var user_id: String? = null
    var session_token: String? = null
    var order_id: String? = null
    var shop_id: String? = null
    var order_date: String? = null
    var product_list: List<NeworderScrCartFragment.NewOrderRoomData>? = null
}