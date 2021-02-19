package com.saidel.bookdex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.bumptech.glide.Glide
import com.saidel.bookdex.databinding.ActivityDetailsPkmBinding
import com.saidel.bookdex.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.StringReader
import java.net.URL

class DetailsPkm : AppCompatActivity(){

    lateinit var binding: ActivityDetailsPkmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsPkmBinding.inflate(this.layoutInflater)
        val view = binding.root
        setContentView(view)

        val actionBar = supportActionBar
        actionBar!!.hide()
        actionBar!!.title = "#"+intent.getStringExtra(Constants.PKM_NUMBER)+" - "+intent.getStringExtra(Constants.PKM_NAME)
        var url = Constants.API_URL_PKM_DETAILS+intent.getStringExtra(Constants.PKM_NUMBER)

        var url_more_details = Constants.API_URL_PKM_MORE_DETAILS+intent.getStringExtra(Constants.PKM_NUMBER)

        val klaxon = Klaxon()
        GlobalScope.launch {
            val apiResponse_pkm_details = URL(url).readText()
            val apiResponse_pkm_more_details = URL(url).readText()
            var parsed_details = klaxon.parseJsonObject(StringReader(apiResponse_pkm_details))
            var parsed_more_details = klaxon.parseJsonObject(StringReader(apiResponse_pkm_more_details))
            withContext(context = Dispatchers.Main) {
                processData(parsed_details)
                processDataMoreDetails(parsed_more_details)
            }
        }
    }

    private fun processData(parsed: JsonObject) {
        var url_default_pkm_image = (parsed["sprites"] as JsonObject)["front_default"]
        Glide.with(this)
                .load(url_default_pkm_image)
                .into(binding.pkmImage)
        var types = (parsed["types"] as JsonArray<*>)
        var types_size = types.size
        for (i in 0 until types_size) {
            binding.pkmDetails.append((((types.get(i) as JsonObject).get("type") as JsonObject).get("name")).toString().toUpperCase()+'\n')
        }
        binding.pkmDetails.append((parsed["weight"].toString().toDouble()/10).toString() + " kg\n")
        binding.pkmDetails.append((parsed["height"].toString().toDouble()/10).toString() + " m\n")
    }

    private fun processDataMoreDetails(parsed: JsonObject) {

    }
}