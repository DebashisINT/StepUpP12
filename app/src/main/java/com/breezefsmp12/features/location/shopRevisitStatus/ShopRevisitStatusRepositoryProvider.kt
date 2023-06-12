package com.breezefsmp12.features.location.shopRevisitStatus

import com.breezefsmp12.features.location.shopdurationapi.ShopDurationApi
import com.breezefsmp12.features.location.shopdurationapi.ShopDurationRepository

object ShopRevisitStatusRepositoryProvider {
    fun provideShopRevisitStatusRepository(): ShopRevisitStatusRepository {
        return ShopRevisitStatusRepository(ShopRevisitStatusApi.create())
    }
}