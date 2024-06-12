package com.ptwelvebreeze.features.activities.api

import com.ptwelvebreeze.features.member.api.TeamApi
import com.ptwelvebreeze.features.member.api.TeamRepo

object ActivityRepoProvider {
    fun activityRepoProvider(): ActivityRepo {
        return ActivityRepo(ActivityApi.create())
    }

    fun activityImageRepoProvider(): ActivityRepo {
        return ActivityRepo(ActivityApi.createImage())
    }
}