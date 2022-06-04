package com.example.tutu_intern_test_cherniak.presentation.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.tutu_intern_test_cherniak.R

fun ImageView.loadImage(imageUrl: String){
    Glide.with(this.context)
        .load(imageUrl)
        .centerCrop()
        .placeholder(R.drawable.placeholder_pokemon_ball)
        .error(R.drawable.error_drawable)
        .into(this)
}