package com.ptwelvebreeze.features.stockAddCurrentStock.api

import com.ptwelvebreeze.features.location.shopRevisitStatus.ShopRevisitStatusApi
import com.ptwelvebreeze.features.location.shopRevisitStatus.ShopRevisitStatusRepository

object ShopAddStockProvider {
    fun provideShopAddStockRepository(): ShopAddStockRepository {
        return ShopAddStockRepository(ShopAddStockApi.create())
    }
}