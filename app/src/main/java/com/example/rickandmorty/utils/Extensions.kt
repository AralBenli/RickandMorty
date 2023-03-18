package com.example.rickandmorty.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

object Extensions {

    infix fun ImageView.setImage(url: Int) {
        Glide.with(context)
            .load(url)
            .fitCenter()
            .into(this)
    }

}