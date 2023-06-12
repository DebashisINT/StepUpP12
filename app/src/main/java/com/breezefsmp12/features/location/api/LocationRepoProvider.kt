package com.breezefsmp12.features.location.api

import com.breezefsmp12.features.location.shopdurationapi.ShopDurationApi
import com.breezefsmp12.features.location.shopdurationapi.ShopDurationRepository


object LocationRepoProvider {
    fun provideLocationRepository(): LocationRepo {
        return LocationRepo(LocationApi.create())
    }
}