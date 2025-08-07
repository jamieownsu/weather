package com.chalupin.practice.core.di

import com.chalupin.practice.data.api.WeatherService
import com.chalupin.practice.data.repository.WeatherRepositoryImpl
import com.chalupin.practice.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://carfax-for-consumers.firebaseio.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideListingService(retrofit: Retrofit): WeatherService {
        return retrofit.create(WeatherService::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository {
        return weatherRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}