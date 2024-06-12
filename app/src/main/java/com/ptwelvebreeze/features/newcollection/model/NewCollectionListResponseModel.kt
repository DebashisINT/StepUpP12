package com.ptwelvebreeze.features.newcollection.model

import com.ptwelvebreeze.app.domain.CollectionDetailsEntity
import com.ptwelvebreeze.base.BaseResponse
import com.ptwelvebreeze.features.shopdetail.presentation.model.collectionlist.CollectionListDataModel

/**
 * Created by Saikat on 15-02-2019.
 */
class NewCollectionListResponseModel : BaseResponse() {
    //var collection_list: ArrayList<CollectionListDataModel>? = null
    var collection_list: ArrayList<CollectionDetailsEntity>? = null
}