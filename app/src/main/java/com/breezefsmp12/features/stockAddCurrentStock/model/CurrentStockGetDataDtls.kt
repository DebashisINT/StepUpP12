package com.breezefsmp12.features.stockAddCurrentStock.model

import com.breezefsmp12.features.stockCompetetorStock.model.ComGetProduct

class CurrentStockGetDataDtls {
    var visited_datetime: String? = null
    var stock_id: String? = null
    var shop_id: String? = null
    var total_qty: String? = null
    var product_list:ArrayList<CurrGetProduct>? = null
}