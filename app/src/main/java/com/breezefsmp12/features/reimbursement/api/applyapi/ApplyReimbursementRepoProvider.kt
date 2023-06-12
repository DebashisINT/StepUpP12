package com.breezefsmp12.features.reimbursement.api.applyapi

/**
 * Created by Saikat on 25-01-2019.
 */
object ApplyReimbursementRepoProvider {
    fun applyReimbursementConfigRepository(): ApplyReimbursementRepo {
        return ApplyReimbursementRepo(ApplyReimbursementApi.create())
    }
}