package com.saidel.bookdex

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.bumptech.glide.Glide
import com.saidel.bookdex.databinding.ActivityDetailsPkmBinding
import com.saidel.bookdex.databinding.ActivityMainBinding
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

        var url = "https://pokeapi.co/api/v2/pokemon/"+intent.getStringExtra(Constants.PKM_NUMBER)+"/"

        val klaxon = Klaxon()
        GlobalScope.launch {
            val apiResponse = URL(url).readText()
            var parsed = klaxon.parseJsonObject(StringReader(apiResponse))
            withContext(context = Dispatchers.Main) {
                processData(parsed)
            }
        }
    }

    private fun processData(parsed: JsonObject) {
        var url_default = (parsed["sprites"] as JsonObject)["front_default"]
        Glide.with(this)
                .load(url_default)
                .into(binding.pkmImage)
        println("rfsaidel:"+url_default)

    }

}