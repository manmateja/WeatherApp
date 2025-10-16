package com.example.weatherapp.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.local.WeatherEntity
import com.example.weatherapp.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class WeatherUiState(
    val isLoading: Boolean = false,
    val weather: WeatherEntity? = null,
    val error: String? = null,
    val cityQuery: String = ""
)

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repo: WeatherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState

    fun onCityChange(city: String) {
        _uiState.value = _uiState.value.copy(cityQuery = city)
    }

    fun fetchWeather(apiKey: String) {
        val city = _uiState.value.cityQuery
        if (city.isBlank()) {
            _uiState.value = _uiState.value.copy(error = "City name cannot be empty")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            val result = repo.getWeather(city, apiKey)

            result.onSuccess { weather ->
                _uiState.value = _uiState.value.copy(isLoading = false, weather = weather)
            }.onFailure { throwable ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = throwable.localizedMessage ?: "Unknown error"
                )
            }
        }
    }
}

@Preview
@Composable
fun WeatherScreenPreview() {
    WeatherScreen(apiKey = "test")
}