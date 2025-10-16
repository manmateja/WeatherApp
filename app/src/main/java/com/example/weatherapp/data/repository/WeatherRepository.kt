package com.example.weatherapp.data.repository

import android.util.Log
import com.example.weatherapp.data.local.WeatherDao
import com.example.weatherapp.data.local.WeatherEntity
import com.example.weatherapp.data.remote.WeatherApi
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: WeatherApi,
    private val dao: WeatherDao
) {
    suspend fun getWeather(city: String, apiKey: String): Result<WeatherEntity> {
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
            Result.success(entity)
        } catch (e: CancellationException) {
            throw e // coroutine was cancelled — rethrow it
        } catch (e: IOException) {
            Log.e("WeatherRepository", "Network error: ${e.localizedMessage}")
            dao.getWeatherByCity(city)?.let {
                Result.success(it) // fallback to cache
            } ?: Result.failure(e)
        } catch (e: HttpException) {
            Log.e("WeatherRepository", "API error: ${e.code()} ${e.message}")
            Result.failure(e) // no cache fallback for API-level error
        } catch (e: Exception) {
            Log.e("WeatherRepository", "Unexpected error: ${e.localizedMessage}")
            Result.failure(e)
        }
    }
}