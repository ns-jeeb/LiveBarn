package com.example.livebarnapp

import android.util.JsonReader
import android.util.Log
import com.example.livebarnapp.models.Server
import com.example.livebarnapp.models.SurfaceItem
import java.io.BufferedReader
import java.io.IOException

class UtilAndConstant {
    companion object{
        const val url = "https://2hsjstzo71.execute-api.us-east-1.amazonaws.com/prod/livebarn-interview-project"
    }
    @Throws(IOException::class)
    fun readJsonStream(response:BufferedReader?): List<SurfaceItem>? {
        val reader = JsonReader(response)
        return try {
            readSurfaceArray(reader)
        } finally {
            reader.close()
        }
    }

    @Throws(IOException::class)
    fun readSurfaceArray(reader: JsonReader): List<SurfaceItem>? {
        val surfaceItem: MutableList<SurfaceItem> = ArrayList()
        reader.beginArray()
        while (reader.hasNext()) {
            surfaceItem.add(readSurItem(reader)!!)
        }
        reader.endArray()
        return surfaceItem
    }
//    "id": 9,
//    "surfaceName": "Rink #1",
//    "status": "OK",
//    "venueName": "Arena Repentigny",
//    "sport": "Hockey",
//    "server": {
//    "id": 17594,
//    "ip4": "0.0.0.17594",
//    "dns": "test.server_17594.livebarn.com"

    @Throws(IOException::class)
    fun readSurItem(reader: JsonReader): SurfaceItem? {
        var id: Int = -1
        var surfaceName: String? = null
        var serverObj: Server? = null
        var status :String =""
        var venueName:String =""
        var sport: String = ""
        reader.beginObject()
        while (reader.hasNext()) {
            val name = reader.nextName()
            if (name == "id") {
                id = reader.nextInt()
            } else if (name == "surfaceName") {
                surfaceName = reader.nextString()
            }else if (name == "sport") {
                sport = reader.nextString()
            } else if (name == "status") {
                status = reader.nextString()
            } else if (name.equals("server")){
                serverObj = readServer(reader)!!
            } else if (name.equals("venueName")){
                venueName = reader.nextString()
            }
            else {
                reader.skipValue()
            }
        }
        reader.endObject()
//        Log.e("readSurItem","$id")
        return SurfaceItem(id, serverObj!!, sport, status,surfaceName!!,venueName)
    }

    @Throws(IOException::class)
    fun readServer(reader: JsonReader): Server? {

        reader.beginObject()
            var id: Int = 0
            var ip: String = ""
            var dns: String = ""
        while (reader.hasNext()) {
            var name = reader.nextName()
            if (name == "id") {
                id = reader.nextInt()
            } else if (name == "ip4") {
                ip = reader.nextString()
            } else if (name == "dns"){
                dns = reader.nextString()
            }
            else  {
                reader.skipValue()
            }
        }
//        Log.e("ReaderServer","$dns")
        reader.endObject()
        return Server(dns  ,id, ip)
    }
//    "server": {
//        "id": 17594,
//        "ip4": "0.0.0.17594",
//        "C": "test.server_17594.livebarn.com"
//    }
}