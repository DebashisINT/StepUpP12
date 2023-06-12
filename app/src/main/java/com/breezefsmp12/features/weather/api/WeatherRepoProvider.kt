package com.breezefsmp12.features.weather.api

import com.breezefsmp12.features.task.api.TaskApi
import com.breezefsmp12.features.task.api.TaskRepo

object WeatherRepoProvider {
    fun weatherRepoProvider(): WeatherRepo {
        return WeatherRepo(WeatherApi.create())
    }
}