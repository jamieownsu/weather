package com.chalupin.practice.domain.enum

enum class WeatherCodes(val codes: List<Int>, val description: String) {
    CLEAR_SKY(listOf(0), "Clear sky"),
    CLOUDS_INCREASING(listOf(1, 2, 3), "Clouds increasing (from clear to overcast)"),
    SMOKE_OR_HAZE(listOf(4), "Smoke or haze"),
    HAZE(listOf(5), "Haze"),
    WIDESPREAD_DUST(listOf(6), "Widespread dust in suspension"),
    MIST(listOf(10), "Mist"),
    SHALLOW_FOG_PATCHES(listOf(11, 12), "Shallow fog patches"),
    PRECIPITATION_FOG_OR_THUNDERSTORM(
        listOf(20, 21, 22, 23, 24, 25, 26, 27, 28, 29),
        "Precipitation, fog, or thunderstorm in the preceding hour, but not at the time of observation"
    ),
    DUSTSTORM_SANDSTORM_OR_BLOWING_SNOW(
        listOf(30, 31, 32, 33, 34, 35, 36, 37, 38, 39),
        "Duststorm, sandstorm, or blowing snow"
    ),
    FOG_OR_ICE_FOG(listOf(40, 41, 42, 43, 44, 45, 46, 47, 48, 49), "Fog or ice fog"),
    DRIZZLE(listOf(50, 51, 52, 53, 54, 55, 56, 57, 58, 59), "Drizzle"),
    LIGHT_DRIZZLE(listOf(51), "Light drizzle"),
    MODERATE_DRIZZLE(listOf(53), "Moderate drizzle"),
    HEAVY_DRIZZLE(listOf(55), "Heavy drizzle"),
    LIGHT_FREEZING_DRIZZLE(listOf(56), "Light freezing drizzle"),
    MODERATE_HEAVY_FREEZING_DRIZZLE(listOf(57), "Moderate or heavy freezing drizzle"),
    RAIN(listOf(60, 61, 62, 63, 64, 65, 66, 67, 68, 69), "Rain"),
    LIGHT_RAIN(listOf(61), "Light rain"),
    MODERATE_RAIN(listOf(63), "Moderate rain"),
    HEAVY_RAIN(listOf(65), "Heavy rain"),
    LIGHT_FREEZING_RAIN(listOf(66), "Light freezing rain"),
    MODERATE_HEAVY_FREEZING_RAIN(listOf(67), "Moderate or heavy freezing rain"),
    SOLID_PRECIPITATION(
        listOf(70, 71, 72, 73, 74, 75, 76, 77, 78, 79),
        "Solid precipitation (snow, snow grains, ice pellets)"
    ),
    LIGHT_SNOWFALL(listOf(71), "Light snowfall"),
    MODERATE_SNOWFALL(listOf(73), "Moderate snowfall"),
    HEAVY_SNOWFALL(listOf(75), "Heavy snowfall"),
    SHOWERS(listOf(80, 81, 82, 83, 84, 85, 86, 87, 88, 89), "Showers"),
    LIGHT_RAIN_SHOWERS(listOf(80), "Light rain showers"),
    MODERATE_HEAVY_RAIN_SHOWERS(listOf(81), "Moderate or heavy rain showers"),
    VIOLENT_RAIN_SHOWERS(listOf(82), "Violent rain showers"),
    RAIN_AND_SNOW_MIXED_SHOWERS(listOf(83, 84), "Rain and snow mixed showers"),
    SNOW_SHOWERS(listOf(85, 86), "Snow showers"),
    SHOWER_OF_SNOW_PELLETS_OR_HAIL(listOf(87, 88, 89), "Shower(s) of snow pellets or small hail"),
    THUNDERSTORM(listOf(90, 91, 92, 93, 94, 95, 96, 97, 98, 99), "Thunderstorm"),
    THUNDERSTORM_WITHOUT_HAIL(listOf(95), "Thunderstorm, slight or moderate, without hail"),
    THUNDERSTORM_WITH_HAIL(listOf(96), "Thunderstorm, slight or moderate, with hail"),
    THUNDERSTORM_HEAVY_WITH_HAIL(listOf(99), "Thunderstorm, heavy, with hail");

    companion object {
        fun getDescriptionForCode(code: Int): String {
            return entries.firstOrNull { it.codes.contains(code) }?.description
                ?: "Unknown"
        }
    }
}