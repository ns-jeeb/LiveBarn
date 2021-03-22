package com.example.livebarnapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livebarnapp.models.SurfaceItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection


@RequiresApi(Build.VERSION_CODES.KITKAT)
class SurfaceViewModel: ViewModel() {

    var _mySurface: MutableLiveData<List<SurfaceItem>> = MutableLiveData()

    var utilConstin: UtilAndConstant = UtilAndConstant()
    var result = ArrayList<SurfaceItem>()

    val mHokeyItems: LiveData<ArrayList<SurfaceItem>>
    var _hokeyItems = MutableLiveData<ArrayList<SurfaceItem>>()

    val mBaseballItems: LiveData<ArrayList<SurfaceItem>>
    var _baseballItems = MutableLiveData<ArrayList<SurfaceItem>>()

    val mBasketItems: LiveData<ArrayList<SurfaceItem>>
    var _basketItems = MutableLiveData<ArrayList<SurfaceItem>>()

    val mVolleballItems: LiveData<ArrayList<SurfaceItem>>
    var _volleballItems = MutableLiveData<ArrayList<SurfaceItem>>()

    val mSoccerItems: LiveData<ArrayList<SurfaceItem>>
    var _soccerItems = MutableLiveData<ArrayList<SurfaceItem>>()

    var thumbnail1: Bitmap? = null
    var thumbnail2: Bitmap? = null

    init {
        mSoccerItems = _soccerItems
        mVolleballItems = _volleballItems
        mBasketItems = _basketItems
        mBaseballItems = _baseballItems
        mHokeyItems = _hokeyItems

        viewModelScope.launch {
            httpGet(UtilAndConstant.url)
            goThroughList(result)
            _mySurface.postValue(result)
        }
    }

    suspend fun httpGet(myURL: String) {
        var urlConnection: HttpURLConnection? = null
        val url = URL(UtilAndConstant.url)
        try {
            withContext(Dispatchers.IO) {
                urlConnection = url.openConnection() as HttpURLConnection
                urlConnection?.setRequestMethod("GET")
                urlConnection?.connect()
                var inputStream: InputStream? = urlConnection?.inputStream ?: null // Nothing to do.
                result = utilConstin?.readJsonStream(inputStream?.bufferedReader()) as ArrayList<SurfaceItem>
            }
        }catch (e: IOException){
            Log.e("Error", "Result ${e.stackTraceToString()}");
        }
    }
    fun goThroughList(sportType: ArrayList<SurfaceItem>) {

        var valleball=  ArrayList<SurfaceItem>()
        var basketball= ArrayList<SurfaceItem>()
        var soccer = ArrayList<SurfaceItem>()
        var hokey = ArrayList<SurfaceItem>()
        var baseball = ArrayList<SurfaceItem>()
        if (sportType.size!= 0){
            for (i in 1 until sportType.size ){
                when(sportType[i].sport){
                    "Hockey" -> hokey.add(sportType[i])
                    "Baseball" -> baseball.add(sportType[i])
                    "Soccer" -> soccer.add(sportType[i])
                    "Volleyball" -> valleball.add(sportType[i])
                    "Basketball" -> basketball.add(sportType[i])
                    else ->  Log.e("List_of_venueNames", "No Items")
                }
            }

            _baseballItems.postValue(sortingVenueList(baseball))
            _soccerItems.postValue(sortingVenueList(soccer))
            _volleballItems.postValue(sortingVenueList(valleball))
            _hokeyItems.postValue(sortingVenueList(hokey))
            _basketItems.postValue(sortingVenueList(basketball))
            Log.e("List_of_venueNames", "${hokey.size}")
        }
    }

    fun setListToAdapter(it: java.util.ArrayList<SurfaceItem>) {
        _mySurface.postValue(it!!)
    }

    fun getListToAdapter() :MutableLiveData<ArrayList<SurfaceItem>>{
        return _mySurface as MutableLiveData<ArrayList<SurfaceItem>>
    }
    fun sortingVenueList(surfaceItem: ArrayList<SurfaceItem>): ArrayList<SurfaceItem>{
        surfaceItem.sortWith(compareBy{it.venueName})
        return surfaceItem
    }

    fun getThumbnail1Bitmap(): Bitmap? {
        if (thumbnail1 == null) {
            viewModelScope.launch {
                getBitmap(0)
            }
        }
        return thumbnail1
    }

    fun getThumbnail2Bitmap(): Bitmap? {
        if (thumbnail2 == null) {
            viewModelScope.launch {
                getBitmap(1)
            }
        }
        return thumbnail2
    }

    suspend fun getBitmap(bitmapNum: Int) {
        var bitmapUrl = when (bitmapNum) {
            0 -> "https://via.placeholder.com/300/808080"
            else -> "https://via.placeholder.com/300/202020"
        }
        var urlConnection: HttpsURLConnection? = null
        val url = URL(bitmapUrl)
        try {
            withContext(Dispatchers.IO) {
                urlConnection = url.openConnection() as HttpsURLConnection
                urlConnection?.doInput = true
                urlConnection?.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                urlConnection?.connect()
                var inputStream: InputStream? = urlConnection?.inputStream ?: null // Nothing to do.
                val bitmap = BitmapFactory.decodeStream(inputStream)
                when (bitmapNum) {
                    0 -> thumbnail1 = bitmap
                    else -> thumbnail2 = bitmap
                }
            }
        }catch (e: IOException){
            Log.e("Error", "Result ${e.stackTraceToString()}");
        }
    }
}
interface OnShareWithSportInterface{
    fun onShowSoccer(soccer: ArrayList<String>)
    fun onShowBaseBall(baseball: ArrayList<String>)
    fun onShowBasketball(basketball: ArrayList<String>)
    fun onShowHokey(hokey: ArrayList<String>)
    fun onShowVolleyball(volleyball: ArrayList<String>)
}
