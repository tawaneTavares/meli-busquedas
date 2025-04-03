package com.tawane.data.di

import com.tawane.data.local.ILastViewedLocalData
import com.tawane.data.remote.datasource.IRemoteDataSource
import com.tawane.data.repository.MeliRepository
import com.tawane.domain.repository.IMeliRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesRepositoryImpl(dataSource: IRemoteDataSource, localData: ILastViewedLocalData): IMeliRepository =
        MeliRepository(dataSource, localData)
}
