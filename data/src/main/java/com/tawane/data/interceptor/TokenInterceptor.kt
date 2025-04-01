package com.tawane.data.interceptor

import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

const val AUTH_HEADER = "Authorization"
const val ACCEPT_HEADER = "accept"
const val APPLICATION_JSON_HEADER = "application/json"

class TokenInterceptor @Inject constructor(private val tokenProvider: TokenProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenProvider.getToken()
        val requestBuilder = chain.request()
            .newBuilder()
            .addHeader(ACCEPT_HEADER, APPLICATION_JSON_HEADER)
            .addHeader(AUTH_HEADER, "Bearer $token")
            .build()

        val response = chain.proceed(requestBuilder)
        return response
    }
}
