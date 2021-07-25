package com.saidel.bookdex.presentation.pkmDetails

import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.saidel.bookdex.R
import com.saidel.bookdex.model.PkmDetails
import com.saidel.bookdex.utils.Constants

class PkmDetailsActivity : AppCompatActivity() {

    private val pkmDetailsViewModel: PkmDetailsViewModel by viewModels()

    lateinit var pkmImage: AppCompatImageView
    lateinit var pkmDetails: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_pkm)
        supportActionBar?.hide()

        setElements()
        setObserver()

        pkmDetailsViewModel.fetchPkmDetailsData(
            PkmDetails(
                number = intent.getStringExtra(Constants.PKM_NUMBER) ?: "",
                name = intent.getStringExtra(Constants.PKM_NAME) ?: ""
            )
        )
    }

    private fun setElements() {
        pkmImage = findViewById(R.id.pkm_image)
        pkmDetails = findViewById(R.id.pkm_details)
    }

    private fun setObserver() {
        pkmDetailsViewModel.pkmDetails.observe(this) { pkm ->
            if (pkm.image_url.contains(".svg")) {
                GlideToVectorYou
                    .init()
                    .with(this)
                    .setPlaceHolder(
                        R.drawable.ic_img_placeholder_pokebola,
                        R.drawable.ic_img_placeholder_pokebola
                    )
                    .load(Uri.parse(pkm.image_url), pkmImage)
            } else {
                Glide.with(this)
                    .load(pkm.image_url)
                    .placeholder(R.drawable.ic_img_placeholder_pokebola)
                    .into(pkmImage)
            }
            pkmDetails.text = pkm.name
        }
    }
}