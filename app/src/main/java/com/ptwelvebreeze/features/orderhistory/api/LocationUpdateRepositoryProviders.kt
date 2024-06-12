package com.ptwelvebreeze.features.orderhistory.api

/**
 * Created by Pratishruti on 23-11-2017.
 */
object LocationUpdateRepositoryProviders {
    fun provideLocationUpdareRepository(): LocationUpdateRepository {
        return LocationUpdateRepository(LocationUpdateApi.create())
    }
}