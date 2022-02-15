package com.example.southsystemtest.utils

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.example.southsystemtest.R
import com.skydoves.androidveil.VeilRecyclerFrameView

fun View.setVisible(
    visible: Boolean,
    useInvisible: Boolean = false,
) {
    visibility = when {
        visible -> View.VISIBLE
        useInvisible -> View.INVISIBLE
        else -> View.GONE
    }
}

fun ImageView.loadImage(
    url: String?,
    options: RequestOptions? = null,
    simpleTarget: CustomTarget<Drawable>? = null,
): Boolean {
    try {
        val optionsToApply = options ?: RequestOptions()
            .placeholder(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.placeholder_photo
                )
            )
            .centerCrop()
        if (simpleTarget == null) {
            Glide.with(context).load(url).apply(optionsToApply).into(this)
        } else {
            Glide.with(context).load(url).apply(optionsToApply).into(simpleTarget)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        return false
    }
    return true
}

fun VeilRecyclerFrameView.setQtdItemsDefault(items: Int = 5) {
    addVeiledItems(items)
}

fun VeilRecyclerFrameView.displayVeil(isVisible: Boolean) {
    if (isVisible) {
        veil()
    } else {
        unVeil()
    }
}