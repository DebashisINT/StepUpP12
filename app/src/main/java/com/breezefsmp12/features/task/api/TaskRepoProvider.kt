package com.breezefsmp12.features.task.api

import com.breezefsmp12.features.timesheet.api.TimeSheetApi
import com.breezefsmp12.features.timesheet.api.TimeSheetRepo

/**
 * Created by Saikat on 12-Aug-20.
 */
object TaskRepoProvider {
    fun taskRepoProvider(): TaskRepo {
        return TaskRepo(TaskApi.create())
    }
}