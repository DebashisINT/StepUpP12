package com.breezefsmp12.features.document.api

import com.breezefsmp12.features.dymanicSection.api.DynamicApi
import com.breezefsmp12.features.dymanicSection.api.DynamicRepo

object DocumentRepoProvider {
    fun documentRepoProvider(): DocumentRepo {
        return DocumentRepo(DocumentApi.create())
    }

    fun documentRepoProviderMultipart(): DocumentRepo {
        return DocumentRepo(DocumentApi.createImage())
    }
}