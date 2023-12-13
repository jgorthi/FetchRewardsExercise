package com.fetchrewards.data

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response


class DataRequest(private val client: OkHttpClient) {
    fun getRequest(url: String) =  client.newCall(Request.Builder().url(url).build()).execute()
}
