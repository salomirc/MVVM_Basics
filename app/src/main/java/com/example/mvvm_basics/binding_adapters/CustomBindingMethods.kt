package com.example.mvvm_basics.binding_adapters

import android.widget.ImageView
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods

@BindingMethods(value = [
    BindingMethod(type = ImageView::class, attribute = "android:tint", method = "setImageTintList"),
    BindingMethod(type = ImageView::class, attribute = "android:tintMode", method = "setImageTintMode"),
    BindingMethod(type = ImageView::class, attribute = "app:srcCompat", method = "setImageResource")

])

class CustomBindingMethods {

}