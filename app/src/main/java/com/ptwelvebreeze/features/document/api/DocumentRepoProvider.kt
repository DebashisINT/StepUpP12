package com.ptwelvebreeze.features.document.api

import com.ptwelvebreeze.features.dymanicSection.api.DynamicApi
import com.ptwelvebreeze.features.dymanicSection.api.DynamicRepo

object DocumentRepoProvider {
    fun documentRepoProvider(): DocumentRepo {
        return DocumentRepo(DocumentApi.create())
    }

    fun documentRepoProviderMultipart(): DocumentRepo {
        return DocumentRepo(DocumentApi.createImage())
    }
}