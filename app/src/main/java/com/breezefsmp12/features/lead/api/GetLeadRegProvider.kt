package com.breezefsmp12.features.lead.api

import com.breezefsmp12.features.NewQuotation.api.GetQuotListRegRepository
import com.breezefsmp12.features.NewQuotation.api.GetQutoListApi


object GetLeadRegProvider {
    fun provideList(): GetLeadListRegRepository {
        return GetLeadListRegRepository(GetLeadListApi.create())
    }
}