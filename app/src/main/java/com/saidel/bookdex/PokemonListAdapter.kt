package com.saidel.bookdex

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.beust.klaxon.JsonObject
import com.bumptech.glide.Glide
import com.saidel.bookdex.model.Pkm
import com.saidel.bookdex.utils.Constants

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
            it.bindView(pkmItem, context)
        }
    }
    override fun getItemCount(): Int {
        return pkms.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindView(pkmItem: Pkm, context: Context) {
            val pkmNumber = itemView.findViewById<TextView>(R.id.pkm_number)
            val pkmName = itemView.findViewById<TextView>(R.id.pkm_name)
            val pkmImage = itemView.findViewById<ImageView>(R.id.pkm_image)

            pkmNumber.text = pkmItem.number.toString()
            pkmName.text = pkmItem.name
            //pkmImage.setImageResource(context.resources.getIdentifier("drawable/pkm_" + pkmItem.number, null, context.getPackageName()))
            var url_default_pkm_image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+pkmItem.number+".png"
            Glide.with(context)
                    .load(url_default_pkm_image)
                    .into(pkmImage)
        }
    }

    override fun onClick(v: View?) {
        Toast.makeText(context,(v?.findViewById<TextView>(R.id.pkm_name))?.text, Toast.LENGTH_SHORT).show()
        val open_details = Intent(context, DetailsPkm::class.java)
        open_details.putExtra(Constants.PKM_NUMBER, (v?.findViewById<TextView>(R.id.pkm_number))?.text)
        open_details.putExtra(Constants.PKM_NAME, (v?.findViewById<TextView>(R.id.pkm_name))?.text)
        context.startActivity(open_details)
    }

}

