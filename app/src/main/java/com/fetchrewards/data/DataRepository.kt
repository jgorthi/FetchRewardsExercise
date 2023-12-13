package com.fetchrewards.data

class DataRepository(private val dataRequest: DataRequest) {
    fun fetchDataFromUrl(url: String) = dataRequest.getRequest(url)
}