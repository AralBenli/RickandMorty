package com.example.rickandmorty.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

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

fun getCurrentDayAndTime(date:String) : String {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
    val date = simpleDateFormat.parse(date)
    val calendar = Calendar.getInstance()
    calendar.time = date

    val dateToWeekNameFormat: DateFormat = SimpleDateFormat("MM-dd-yyyy", Locale("tr"))
    return dateToWeekNameFormat.format(date)
}