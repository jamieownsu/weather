package com.chalupin.practice.core.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OpenMeteoRetrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @OpenMeteoRetrofit
    fun provideOpenMeteoRetrofit(): Retrofit {
        val gson = GsonBuilder()
//            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeDeserializer())
            .create()
        return Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}