package com.ptwelvebreeze.features.dashboard.presentation.api.dayStartEnd

import com.ptwelvebreeze.features.stockCompetetorStock.api.AddCompStockApi
import com.ptwelvebreeze.features.stockCompetetorStock.api.AddCompStockRepository

object DayStartEndRepoProvider {
    fun dayStartRepositiry(): DayStartEndRepository {
        return DayStartEndRepository(DayStartEndApi.create())
    }

}