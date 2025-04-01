package com.tawane.data.remote.interceptor

import com.tawane.data.BuildConfig
import javax.inject.Inject

class TokenProviderImpl @Inject constructor() : TokenProvider {
    override fun getToken(): String = BuildConfig.API_TOKEN
}
