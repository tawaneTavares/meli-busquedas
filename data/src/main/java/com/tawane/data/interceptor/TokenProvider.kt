package com.tawane.data.interceptor

import com.tawane.data.BuildConfig
import javax.inject.Inject

class TokenProvider @Inject constructor() : ITokenProvider {
    override fun getToken(): String = BuildConfig.API_TOKEN
}
