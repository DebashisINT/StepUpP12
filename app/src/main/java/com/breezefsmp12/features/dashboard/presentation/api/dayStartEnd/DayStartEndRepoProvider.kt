package com.breezefsmp12.features.dashboard.presentation.api.dayStartEnd

import com.breezefsmp12.features.stockCompetetorStock.api.AddCompStockApi
import com.breezefsmp12.features.stockCompetetorStock.api.AddCompStockRepository

object DayStartEndRepoProvider {
    fun dayStartRepositiry(): DayStartEndRepository {
        return DayStartEndRepository(DayStartEndApi.create())
    }

}