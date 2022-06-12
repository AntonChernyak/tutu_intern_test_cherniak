package com.example.tutu_intern_test_cherniak.presentation.extensions

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.example.tutu_intern_test_cherniak.R

fun ImageView.loadImage(imageUrl: String = "", @DrawableRes resources:  Int = 0){
    Glide.with(this.context)
        .load(if (resources == 0) imageUrl else resources)
        .placeholder(R.drawable.placeholder_pokemon_ball)
        .error(R.drawable.error_drawable)
        .into(this)
}