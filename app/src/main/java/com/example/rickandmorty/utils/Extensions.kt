package com.example.rickandmorty.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions

object Extensions {

    infix fun ImageView.setImage(url: Int) {
        Glide.with(context)
            .load(url)
            .fitCenter()
            .into(this)
    }

    infix fun ImageView.setImageUrl(url: String) {
        Glide.with(context)
            .load(url)
            .apply(RequestOptions().override(1440, 1080))
            .into(this)
    }
}