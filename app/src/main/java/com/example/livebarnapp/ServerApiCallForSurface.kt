package com.example.livebarnapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.livebarnapp.UtilAndConstant.Companion.url
import com.example.livebarnapp.models.SurfaceItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class ServerApiCallForSurface() {


}

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}