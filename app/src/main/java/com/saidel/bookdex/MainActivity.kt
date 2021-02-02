package com.saidel.bookdex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
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
            var pkm_item = Pkm(i+1,
                            ((pkm_list_json as JsonArray<*>).get(i) as JsonObject).get("name").toString().capitalize(),
                            ((pkm_list_json as JsonArray<*>).get(i) as JsonObject).get("url").toString())
            pkm_list.add(pkm_item)
        }

        var pkmsRecyclerView = this.findViewById<RecyclerView>(R.id.pkm_list)
        pkmsRecyclerView.adapter = PokemonListAdapter(pkm_list, this)
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        pkmsRecyclerView.layoutManager = layoutManager
    }
}