package com.breezefsmp12.features.weather.api

import com.breezefsmp12.base.BaseResponse
import com.breezefsmp12.features.task.api.TaskApi
import com.breezefsmp12.features.task.model.AddTaskInputModel
import com.breezefsmp12.features.weather.model.ForeCastAPIResponse
import com.breezefsmp12.features.weather.model.WeatherAPIResponse
import io.reactivex.Observable

class WeatherRepo(val apiService: WeatherApi) {
    fun getCurrentWeather(zipCode: String): Observable<WeatherAPIResponse> {
        return apiService.getTodayWeather(zipCode)
    }

    fun getWeatherForecast(zipCode: String): Observable<ForeCastAPIResponse> {
        return apiService.getForecast(zipCode)
    }
}