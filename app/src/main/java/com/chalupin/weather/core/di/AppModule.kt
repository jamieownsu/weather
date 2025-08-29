package com.chalupin.weather.core.di

import android.content.Context
import com.chalupin.weather.data.api.WeatherService
import com.chalupin.weather.data.dao.LocationDao
import com.chalupin.weather.data.database.LocationDatabase
import com.chalupin.weather.data.repository.LocationRepositoryImpl
import com.chalupin.weather.data.repository.WeatherRepositoryImpl
import com.chalupin.weather.domain.repository.LocationRepository
import com.chalupin.weather.domain.repository.WeatherRepository
import com.chalupin.weather.presentation.home.util.PermissionChecker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

//    @Provides
//    @Singleton
//    fun provideRetrofit(): Retrofit {
//        val gson = GsonBuilder()
//            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeDeserializer())
//            .create()
//        return Retrofit.Builder()
//            .baseUrl("https://api.open-meteo.com/")
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
//    }

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    @Singleton
    fun providePermissionChecker(@ApplicationContext context: Context): PermissionChecker {
        return PermissionChecker(context)
    }

    @Provides
    @Singleton
    fun provideWeatherService(@OpenMeteoRetrofit retrofit: Retrofit): WeatherService {
        return retrofit.create(WeatherService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): LocationDatabase {
        return LocationDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideListingDao(database: LocationDatabase): LocationDao {
        return database.locationDao()
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository {
        return weatherRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideLocationRepository(locationRepositoryImpl: LocationRepositoryImpl): LocationRepository {
        return locationRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}