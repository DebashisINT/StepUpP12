package com.ptwelvebreeze.features.weather.api

import com.ptwelvebreeze.base.BaseResponse
import com.ptwelvebreeze.features.task.api.TaskApi
import com.ptwelvebreeze.features.task.model.AddTaskInputModel
import com.ptwelvebreeze.features.weather.model.ForeCastAPIResponse
import com.ptwelvebreeze.features.weather.model.WeatherAPIResponse
import io.reactivex.Observable

class WeatherRepo(val apiService: WeatherApi) {
    fun getCurrentWeather(zipCode: String): Observable<WeatherAPIResponse> {
        return apiService.getTodayWeather(zipCode)
    }

    fun getWeatherForecast(zipCode: String): Observable<ForeCastAPIResponse> {
        return apiService.getForecast(zipCode)
    }
}