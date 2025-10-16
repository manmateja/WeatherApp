# WeatherApp

A simple weather application for Android, built with modern Android development technologies.

## Description

This app allows users to check the current weather for a given city. It fetches data from the OpenWeatherMap API and displays the temperature, humidity, and a description of the weather conditions. The app also caches the latest weather data for offline access.

## Features

*   Get current weather by city name.
*   Displays temperature, humidity, and weather description.
*   Shows a corresponding weather icon.
*   Caches the last searched weather information for offline viewing.

Screenshot:
<img width="300" height="500" alt="image" src="https://github.com/user-attachments/assets/3529c98c-36a5-4da2-beac-fcd33f7028dd" />


## Technologies Used

*   **UI:** Jetpack Compose for building the user interface.
*   **Architecture:** MVVM (Model-View-ViewModel).
*   **Dependency Injection:** Hilt for managing dependencies.
*   **Networking:** Retrofit and Gson for making API calls.
*   **Asynchronous Programming:** Kotlin Coroutines for managing background threads.
*   **Database:** Room for local data caching.
*   **Image Loading:** Coil for loading weather icons.

## Setup

1.  Clone the repository.
2.  Open the project in Android Studio.
3.  You will need an API key from [OpenWeatherMap](https://openweathermap.org/api).
4.  Pass the API key to the `WeatherScreen` composable.

## Usage

1.  Enter a city name in the text field.
2.  Click the "Get Weather" button.
3.  The current weather information for the specified city will be displayed.
