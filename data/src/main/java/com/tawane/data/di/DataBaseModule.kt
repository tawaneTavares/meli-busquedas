package com.tawane.data.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.tawane.data.local.AppDataBase
import com.tawane.data.local.ILastViewedLocalData
import com.tawane.data.local.LastViewedLocalData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDataBase = Room.databaseBuilder(
        context,
        AppDataBase::class.java,
        "meli.db",
    ).build()

    @Provides
    fun providesLocalData(appDatabase: AppDataBase, moshi: Moshi): ILastViewedLocalData =
        LastViewedLocalData(appDatabase, moshi)
}
