package com.chalupin.weather.core.di

import com.chalupin.weather.data.repository.RemoteConfigRepositoryImpl
import com.chalupin.weather.domain.repository.RemoteConfigRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteConfigModule {

    @Binds
    @Singleton
    abstract fun bindRemoteConfigRepository(
        remoteConfigRepositoryImpl: RemoteConfigRepositoryImpl
    ): RemoteConfigRepository
}