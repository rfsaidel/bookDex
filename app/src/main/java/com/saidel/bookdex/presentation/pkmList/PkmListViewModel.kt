package com.saidel.bookdex.presentation.pkmList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.saidel.bookdex.model.Pkm
import com.saidel.bookdex.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
            var pkm_item = Pkm(i + 1,
                    ((pkm_list_json as JsonArray<*>).get(i) as JsonObject).get("name").toString().capitalize(),
                    ((pkm_list_json as JsonArray<*>).get(i) as JsonObject).get("url").toString())
            pkm_list.add(pkm_item)
        }
        pkmList.value = pkm_list
    }
}