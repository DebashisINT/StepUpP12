package com.ptwelvebreeze.features.location.shopRevisitStatus

import com.ptwelvebreeze.features.location.shopdurationapi.ShopDurationApi
import com.ptwelvebreeze.features.location.shopdurationapi.ShopDurationRepository

object ShopRevisitStatusRepositoryProvider {
    fun provideShopRevisitStatusRepository(): ShopRevisitStatusRepository {
        return ShopRevisitStatusRepository(ShopRevisitStatusApi.create())
    }
}