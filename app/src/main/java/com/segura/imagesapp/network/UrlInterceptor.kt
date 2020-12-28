package com.segura.imagesapp.network

import com.segura.imagesapp.utils.logDebug
import okhttp3.Interceptor
import okhttp3.Response

class UrlInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {


        val originalRequest = chain.request()
        val authenticationRequest = originalRequest.newBuilder().build()

        logDebug("UrlInterceptor","URL : ${authenticationRequest.url()}")
        return chain.proceed(authenticationRequest)
    }

}