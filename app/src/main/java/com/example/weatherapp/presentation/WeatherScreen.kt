package com.example.weatherapp.presentation

import android.R.attr.apiKey
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapp.data.local.WeatherEntity


@Composable
fun WeatherScreen(
    apiKey: String,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        OutlinedTextField(
            value = state.cityQuery,
            onValueChange = viewModel::onCityChange,
            label = { Text("Enter city") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { viewModel.fetchWeather(apiKey) }) {
            Text("Get Weather")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            state.isLoading -> CircularProgressIndicator()
            state.error != null -> Text(
                text = state.error ?: "",
                color = MaterialTheme.colorScheme.error
            )
            state.weather != null -> {
                Text("${state.weather!!.city} - ${state.weather!!.temperature}°C")
                Text("Humidity: ${state.weather!!.humidity}%")
                Text(state.weather!!.description)

                Image(
                    painter = rememberAsyncImagePainter(
                        "https://openweathermap.org/img/wn/${state.weather!!.icon}@2x.png"
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )
            }
        }
    }
}

@Composable
fun WeatherScreenPreviewContent(state: WeatherUiState) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = state.cityQuery,
            onValueChange = {},
            label = { Text("Enter city") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {}) { Text("Get Weather") }
        Spacer(modifier = Modifier.height(16.dp))

        when {
            state.isLoading -> CircularProgressIndicator()
            state.error != null -> Text(
                text = state.error ?: "",
                color = MaterialTheme.colorScheme.error
            )
            state.weather != null -> {
                Text("${state.weather.city} - ${state.weather.temperature}°C")
                Text("Humidity: ${state.weather.humidity}%")
                Text(state.weather.description)
                Image(
                    painter = rememberAsyncImagePainter(
                        "https://openweathermap.org/img/wn/${state.weather.icon}@2x.png"
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherScreenPreview_FakeData() {
    val fakeWeather = WeatherEntity(
        city = "London",
        temperature = 18.0,
        description = "Sunny",
        humidity = 55,
        icon = "01d"
    )
    WeatherScreenPreviewContent(
        WeatherUiState(
            isLoading = false,
            weather = fakeWeather,
            error = null,
            cityQuery = "London"
        )
    )
}