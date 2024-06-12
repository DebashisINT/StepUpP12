package com.ptwelvebreeze.features.location.api

import com.ptwelvebreeze.features.location.shopdurationapi.ShopDurationApi
import com.ptwelvebreeze.features.location.shopdurationapi.ShopDurationRepository


object LocationRepoProvider {
    fun provideLocationRepository(): LocationRepo {
        return LocationRepo(LocationApi.create())
    }
}