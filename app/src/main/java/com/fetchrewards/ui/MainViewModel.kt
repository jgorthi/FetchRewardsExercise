package com.fetchrewards.ui

import okhttp3.OkHttpClient
import com.fetchrewards.data.DataRequest
import com.fetchrewards.data.DataResponse
import com.fetchrewards.data.convertToMap
import com.fetchrewards.data.DataRepository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beust.klaxon.Klaxon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private var dataRepository: DataRepository = DataRepository(DataRequest(OkHttpClient()))
    var responseUrlData: MutableLiveData<Map<Int, List<String?>>> = MutableLiveData()
    val loadingIndicator = MutableLiveData<Boolean>()

    fun getDataFromUrl(url: String) {
        loadingIndicator.value = true
        CoroutineScope(IO).launch {
            val urlResponse = dataRepository.fetchDataFromUrl(url)
            val body = urlResponse.body?.string()
            if (body != null) {
                val parseJSON: List<DataResponse> = Klaxon().parseArray(body)!!
                val responseData = parseJSON.convertToMap()
                withContext(Main) {
                    responseUrlData.value = responseData
                    loadingIndicator.value = false
                }
            }
            else {
                withContext(Main) {
                    loadingIndicator.value = false
                }
            }
        }
    }
}