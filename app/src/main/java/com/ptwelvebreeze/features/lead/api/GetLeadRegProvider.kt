package com.ptwelvebreeze.features.lead.api

import com.ptwelvebreeze.features.NewQuotation.api.GetQuotListRegRepository
import com.ptwelvebreeze.features.NewQuotation.api.GetQutoListApi


object GetLeadRegProvider {
    fun provideList(): GetLeadListRegRepository {
        return GetLeadListRegRepository(GetLeadListApi.create())
    }
}