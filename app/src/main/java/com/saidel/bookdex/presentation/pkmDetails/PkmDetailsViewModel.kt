package com.saidel.bookdex.presentation.pkmDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.saidel.bookdex.model.PkmDetails
import com.saidel.bookdex.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.StringReader
import java.net.URL

class PkmDetailsViewModel : ViewModel() {

    lateinit var pkm: PkmDetails

    val pkmDetails: MutableLiveData<PkmDetails> by lazy {
        MutableLiveData<PkmDetails>()
    }

    fun fetchPkmDetailsData(pkm: PkmDetails) {
        this.pkm = pkm
        var url = Constants.API_URL_PKM_DETAILS + pkm.number
        var url_more_details = Constants.API_URL_PKM_MORE_DETAILS + pkm.number
        val klaxon = Klaxon()
        GlobalScope.launch {
            val apiResponse_pkm_details = URL(url).readText()
            //val apiResponse_pkm_more_details = URL(url_more_details).readText()
            var parsed_details = klaxon.parseJsonObject(StringReader(apiResponse_pkm_details))
            //var parsed_more_details = klaxon.parseJsonObject(StringReader(apiResponse_pkm_more_details))
            withContext(context = Dispatchers.Main) {
                processData(parsed_details)
                //processDataMoreDetails(parsed_more_details)
            }
        }
    }

    private fun processData(parsed: JsonObject) {
        var best_image_rul = ""
        try {
            best_image_rul =
                (((parsed["sprites"] as JsonObject)["other"] as JsonObject)["dream_world"] as JsonObject)["front_default"] as String
        } catch (exception: Exception) {
            try {
                best_image_rul =
                    (((parsed["sprites"] as JsonObject)["other"] as JsonObject)["official-artwork"] as JsonObject)["front_default"] as String
            } catch (exception: Exception) {
                best_image_rul = (parsed["sprites"] as JsonObject)["front_default"] as String
            }
        }


        pkm.image_url = best_image_rul
        var types = (parsed["types"] as JsonArray<*>)
        var types_size = types.size

        for (i in 0 until types_size) {
            pkm.type.toMutableList()
                .add(((types[i] as JsonObject)["type"] as JsonObject)["name"] as String)
        }
        pkm.weight = ((parsed["weight"].toString().toDouble() / 10).toString()) //kg
        pkm.height = ((parsed["height"].toString().toDouble() / 10).toString()) //m

        pkmDetails.value = pkm
    }

    private fun processDataMoreDetails(parsed: JsonObject) {

    }


}