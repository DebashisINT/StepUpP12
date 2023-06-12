package com.breezefsmp12.features.stockAddCurrentStock.api

import com.breezefsmp12.features.location.shopRevisitStatus.ShopRevisitStatusApi
import com.breezefsmp12.features.location.shopRevisitStatus.ShopRevisitStatusRepository

object ShopAddStockProvider {
    fun provideShopAddStockRepository(): ShopAddStockRepository {
        return ShopAddStockRepository(ShopAddStockApi.create())
    }
}