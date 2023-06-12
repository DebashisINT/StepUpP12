package com.breezefsmp12.features.attendance.model

import com.breezefsmp12.base.BaseResponse

class OutputResponse: BaseResponse() {
    var last_visit_order_list:ArrayList<last_visit_order_list>?=null
}
data class last_visit_order_list(var shop_name:String="",var shop_id:String="",var shop_Type:String="",
                              var shop_TypeName:String="",var last_visited_date:String="",var last_order_date:String="")