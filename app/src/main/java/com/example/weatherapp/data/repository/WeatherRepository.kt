package com.example.weatherapp.data.repository

import com.example.weatherapp.data.local.WeatherDao
import com.example.weatherapp.data.local.WeatherEntity
import com.example.weatherapp.data.remote.WeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: WeatherApi,
    private val dao: WeatherDao
) {
    suspend fun getWeather(city: String, apiKey: String): WeatherEntity? {
        return try {
            val remote = api.getWeatherByCity(city, apiKey)
            val entity = WeatherEntity(
                city = remote.name,
                temperature = remote.main.temp,
                description = remote.weather.firstOrNull()?.description.orEmpty(),
                humidity = remote.main.humidity,
                icon = remote.weather.firstOrNull()?.icon.orEmpty()
            )
            dao.insertWeather(entity)
            entity
        } catch (e: Exception) {
            dao.getWeatherByCity(city) // fallback from cache
        }
    }
}