package com.dennisiluma.hagglex

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.dennisiluma.hagglex.utils.SharedPreferenceManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response


private class AuthorizationInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val sharedPreference = SharedPreferenceManager(context)
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${sharedPreference.getToken()}")
            .build()

        return chain.proceed(request)
    }
}

fun getApolloClient(context: Context): ApolloClient {
    return ApolloClient.builder()
        .serverUrl("https://api-staging.hagglex.com/graphql")
        .okHttpClient(
        OkHttpClient.Builder().
        addInterceptor(AuthorizationInterceptor(context))
            .build()).build()
}