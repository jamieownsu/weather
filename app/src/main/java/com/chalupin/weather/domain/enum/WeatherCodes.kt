package com.chalupin.weather.domain.enum

import androidx.annotation.RawRes
import com.chalupin.weather.R

enum class WeatherCodes(
    val codes: List<Int>,
    val description: String,
    val animatedIcon: Int,
    val icon: Int
) {
    CLEAR_OR_NO_PRECIPITATION(
        listOf(0, 1, 2),
        "Clear",
        R.raw.clear_day, R.drawable.clear_day
    ),
    CLOUDS_FORMING(
        listOf(3),
        "Clouds forming",
        R.raw.cloudy, R.drawable.cloudy
    ),
    SMOKE(
        listOf(4),
        "Smoke",
        R.raw.smoke, R.drawable.smoke
    ),
    HAZE(
        listOf(5),
        "Haze",
        R.raw.haze, R.drawable.haze
    ),
    DUST_OR_SAND(
        listOf(6, 7, 8),
        "Dust or sand",
        R.raw.dust_wind, R.drawable.dust_wind
    ),
    DUSTSTORM_OR_SANDSTORM(
        listOf(9),
        "Duststorm or sandstorm",
        R.raw.duststorm, R.drawable.duststorm
    ),
    MIST(
        listOf(10),
        "Mist",
        R.raw.mist, R.drawable.mist
    ),
    FOG_PATCHES(
        listOf(11, 12, 41),
        "Fog patches",
        R.raw.fog, R.drawable.fog
    ),
    LIGHTNING(
        listOf(13),
        "Lightning visible, no thunder",
        R.raw.thunderstorms, R.drawable.thunderstorms
    ),
    PRECIPITATION_NEARBY(
        listOf(14, 15, 16),
        "Precipitation nearby",
        R.raw.rain, R.drawable.rain
    ),
    THUNDERSTORM(
        listOf(17, 29, 91, 92, 93, 94, 95, 96, 97, 98, 99),
        "Thunderstorm",
        R.raw.thunderstorms, R.drawable.thunderstorms
    ),
    SQUALLS(
        listOf(18),
        "Squalls",
        R.raw.wind, R.drawable.wind
    ),
    FUNNEL_CLOUD(
        listOf(19),
        "Funnel cloud",
        R.raw.tornado, R.drawable.tornado
    ),
    DRIZZLE_RECENT(
        listOf(20),
        "Recent drizzle",
        R.raw.drizzle, R.drawable.drizzle
    ),
    RAIN_RECENT(
        listOf(21),
        "Recent rain",
        R.raw.rain, R.drawable.rain
    ),
    SNOW_RECENT(
        listOf(22),
        "Recent snow",
        R.raw.snow, R.drawable.snow
    ),
    RAIN_AND_SNOW_RECENT(
        listOf(23),
        "Recent rain and snow",
        R.raw.sleet, R.drawable.sleet
    ),
    FREEZING_RAIN_RECENT(
        listOf(24),
        "Recent freezing drizzle or rain",
        R.raw.hail, R.drawable.hail
    ),
    RAIN_SHOWERS_RECENT(
        listOf(25),
        "Recent rain showers",
        R.raw.rain, R.drawable.rain
    ),
    SNOW_SHOWERS_RECENT(
        listOf(26),
        "Recent snow or rain/snow showers",
        R.raw.snow, R.drawable.snow
    ),
    HAIL_SHOWERS_RECENT(
        listOf(27),
        "Recent hail showers",
        R.raw.hail, R.drawable.hail
    ),
    FOG_RECENT(
        listOf(28),
        "Recent fog or ice fog",
        R.raw.fog, R.drawable.fog
    ),
    DUSTSTORM(
        listOf(30, 31, 32, 33, 34, 35),
        "Duststorm or sandstorm",
        R.raw.duststorm, R.drawable.duststorm
    ),
    BLOWING_SNOW(
        listOf(36, 37, 38, 39),
        "Blowing snow",
        R.raw.snow, R.drawable.snow
    ),
    FOG(
        listOf(40, 42, 43, 44, 45, 46, 47, 48, 49),
        "Fog",
        R.raw.fog, R.drawable.fog
    ),
    DRIZZLE(
        listOf(50, 51, 52, 53, 54, 55, 56, 57, 58, 59),
        "Drizzle",
        R.raw.drizzle, R.drawable.drizzle
    ),
    RAIN(
        listOf(60, 61, 62, 63, 64, 65, 66, 67, 68, 69),
        "Rain",
        R.raw.rain, R.drawable.rain
    ),
    SNOW(
        listOf(70, 71, 72, 73, 74, 75, 76, 77, 78),
        "Snow",
        R.raw.snow, R.drawable.snow
    ),
    ICE_PELLETS(
        listOf(79),
        "Ice pellets",
        R.raw.sleet, R.drawable.sleet
    ),
    RAIN_SHOWERS(
        listOf(80, 81, 82),
        "Rain showers",
        R.raw.rain, R.drawable.rain
    ),
    RAIN_AND_SNOW_SHOWERS(
        listOf(83, 84),
        "Rain and snow showers",
        R.raw.sleet, R.drawable.sleet
    ),
    SNOW_SHOWERS(
        listOf(85, 86),
        "Snow showers",
        R.raw.snow, R.drawable.snow
    ),
    HAIL_SHOWERS(
        listOf(87, 88, 89, 90),
        "Hail showers",
        R.raw.hail, R.drawable.hail
    ),
    UNKNOWN(
        listOf(),
        "",
        R.raw.clear_day,
        R.drawable.clear_day
    );

    companion object {
        fun getDescriptionForCode(code: Int): String {
            return entries.firstOrNull { it.codes.contains(code) }?.description
                ?: "Unknown"
        }

        @RawRes
        fun getSvgFile(code: Int): Int {
            return entries.firstOrNull { it.codes.contains(code) }?.icon
                ?: R.drawable.clear_day
        }

        @RawRes
        fun getLottieIconFile(code: Int): Int {
            return entries.firstOrNull { it.codes.contains(code) }?.animatedIcon
                ?: R.raw.clear_day
        }
    }
}