package com.saidel.bookdex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.saidel.bookdex.model.Pkm

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var pkmsRecyclerView = this.findViewById<RecyclerView>(R.id.pkm_list)
        pkmsRecyclerView.adapter = PokemonListAdapter(pkms(), this)
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        pkmsRecyclerView.layoutManager = layoutManager
    }

    private fun pkms(): List<Pkm>{
        return listOf(
            Pkm(1, "Bulbasaur"),
            Pkm(2, "Ivysaur"),
            Pkm(3, "Venusaur"))
    }
}