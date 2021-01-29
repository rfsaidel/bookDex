package com.saidel.bookdex

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.saidel.bookdex.model.Pkm

class PokemonListAdapter(private val pkms: List<Pkm>, private val context: Context) : Adapter<PokemonListAdapter.ViewHolder>(),
    View.OnClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.pkm_item, parent, false)
        view.findViewById<TextView>(R.id.pkm_name)
        view.setOnClickListener(this)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pkmItem = pkms[position]
        holder?.let {
            it.bindView(pkmItem)
        }
    }
    override fun getItemCount(): Int {
        return pkms.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindView(pkmItem: Pkm) {
            val pkmNumber = itemView.findViewById<TextView>(R.id.pkm_number)
            val pkmName = itemView.findViewById<TextView>(R.id.pkm_name)

            pkmNumber.text = pkmItem.number.toString()
            pkmName.text = pkmItem.name
        }
    }

    override fun onClick(v: View?) {
        Toast.makeText(context,(v?.findViewById<TextView>(R.id.pkm_name))?.text, Toast.LENGTH_SHORT).show()
    }

}

