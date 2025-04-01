package com.tawane.data.di

import com.tawane.data.remote.datasource.IRemoteDataSource
import com.tawane.data.remote.datasource.RemoteDataSource
import com.tawane.data.remote.service.MeliService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Singleton
    @Provides
    fun providesRemoteDataSource(service: MeliService): IRemoteDataSource = RemoteDataSource(service)
}
