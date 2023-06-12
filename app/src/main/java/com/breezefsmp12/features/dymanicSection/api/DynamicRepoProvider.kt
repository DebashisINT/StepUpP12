package com.breezefsmp12.features.dymanicSection.api

import com.breezefsmp12.features.dailyPlan.api.PlanApi
import com.breezefsmp12.features.dailyPlan.api.PlanRepo

/**
 * Created by Saikat on 19-Aug-20.
 */
object DynamicRepoProvider {
    fun dynamicRepoProvider(): DynamicRepo {
        return DynamicRepo(DynamicApi.create())
    }

    fun dynamicRepoProviderMultipart(): DynamicRepo {
        return DynamicRepo(DynamicApi.createImage())
    }
}