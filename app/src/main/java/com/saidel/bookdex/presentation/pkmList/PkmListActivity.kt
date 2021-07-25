package com.saidel.bookdex.presentation.pkmList

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.saidel.bookdex.R

class PkmListActivity : AppCompatActivity() {

    val pkmListViewModel: PkmListViewModel by viewModels()

    lateinit var pkmRecycleView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        setElements()
        setObserver()
        pkmListViewModel.fetchPkmListData()
    }

    private fun setElements() {
        pkmRecycleView = findViewById(R.id.pkm_list)
    }

    private fun setObserver() {
        pkmListViewModel.pkmList.observe(this) { list ->
            pkmRecycleView.adapter = PokemonListAdapter(list, this)
            val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            pkmRecycleView.layoutManager = layoutManager

        }
    }
}