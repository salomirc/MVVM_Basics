package com.example.mvvm_basics.binding_adapters

import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion

@BindingAdapter("app:imageResourceCustom")
fun setImageResourceCustom(imageView: ImageView, resId: Int) {
    imageView.setImageResource(resId)
}

@BindingAdapter("app:hideIfFalse")
fun hideIfFalse(view: View, bool: Boolean) {
    view.visibility = if (bool) View.VISIBLE else View.GONE
}

@BindingConversion
fun convertColorToDrawable(color: String) = ColorDrawable(color.toInt())

@BindingConversion
fun convertBoolToVisibility(bool: Boolean) = when (bool){
    true -> View.VISIBLE
    false -> View.GONE
}