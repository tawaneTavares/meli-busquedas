package com.tawane.data.di

import com.tawane.data.interceptor.ITokenProvider
import com.tawane.data.interceptor.TokenProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TokenModule {

    @Binds
    abstract fun bindTokenProvider(tokenProviderImpl: TokenProvider): ITokenProvider
}
