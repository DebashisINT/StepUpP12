package com.ptwelvebreeze.features.weather.api

import com.ptwelvebreeze.features.task.api.TaskApi
import com.ptwelvebreeze.features.task.api.TaskRepo

object WeatherRepoProvider {
    fun weatherRepoProvider(): WeatherRepo {
        return WeatherRepo(WeatherApi.create())
    }
}