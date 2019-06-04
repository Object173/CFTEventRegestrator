package com.object173.cfteventregestrator.feature.base.presentation

import android.graphics.drawable.Drawable
import android.text.Html
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import com.object173.cfteventregestrator.R
import com.object173.cfteventregestrator.feature.base.domain.City
import com.squareup.picasso.Picasso
import java.lang.StringBuilder
import java.text.DateFormat
import java.util.*

@BindingAdapter(value = ["imageUrl", "placeholder"], requireAll = false)
fun AppCompatImageView.loadImage(imageUrl: String?, placeholder : Drawable) {
    Picasso.get()
        .load(context.getString(R.string.base_url) + imageUrl)
        .fit()
        .error(placeholder)
        .placeholder(placeholder)
        .into(this)
}

@BindingAdapter("html")
fun AppCompatTextView.setHtml(text: String?) {
    text?.let{ this.text = Html.fromHtml(text) } ?: this.setText(text)
}

@BindingConversion
fun convertCitiesToString(cities : Array<City>?) : String {
    val builder = StringBuilder()
    cities?.let {
        it.forEach {builder.append(it.nameRus).appendln()}
    }
    return builder.toString()
}

@BindingConversion
fun convertDateToString(date : Date?) : String {
    return date?.let{ DateFormat.getDateInstance(DateFormat.MEDIUM).format(date)} ?: ""
}