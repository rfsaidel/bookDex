package com.saidel.bookdex.presentation.pkmList

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.saidel.bookdex.model.Pkm
import com.saidel.bookdex.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.StringReader
import java.net.URL

class PkmListViewModel : ViewModel() {

    val pkmList: MutableLiveData<List<Pkm>> by lazy {
        MutableLiveData<List<Pkm>>()
    }

    fun fetchPkmListData() {
        val klaxon = Klaxon()
        GlobalScope.launch {
            val apiResponse = URL(Constants.API_URL_PKM_LIST).readText()
            var parsed = klaxon.parseJsonObject(StringReader(apiResponse))
            withContext(context = Dispatchers.Main) {
                processData(parsed)
            }
        }
    }

    private fun processData(parsed: JsonObject) {
        var pkm_list: MutableList<Pkm> = mutableListOf()
        val pkm_list_json = parsed["results"]
        var size = (pkm_list_json as JsonArray<*>).size
        for (i in 0 until size) {
            var pkm_item = Pkm(
                i + 1,
                ((pkm_list_json as JsonArray<*>).get(i) as JsonObject).get("name").toString()
                    .capitalize(),
                ((pkm_list_json as JsonArray<*>).get(i) as JsonObject).get("url").toString()
            )
            pkm_list.add(pkm_item)
        }
        pkmList.value = pkm_list
    }

    fun postData(context: Context) {
        val queue: RequestQueue = Volley.newRequestQueue(context)

        val request: StringRequest = object : StringRequest(
            Method.POST,
            Constants.API_URL_PKM_LIST_ADV,
            Response.Listener { response ->
                try {
                    Log.i("rfsaidel", response.toString())
                    val respObj = JSONObject(response)
                    val name = respObj.getString("name")
                    val job = respObj.getString("job")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { error -> // method to handle errors.
                Toast.makeText(
                    context,
                    "Fail to get response = $error",
                    Toast.LENGTH_SHORT
                ).show()
            }) {
        }
        queue.add(request)
    }
}