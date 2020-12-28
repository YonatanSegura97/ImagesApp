package com.segura.imagesapp.utils

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import org.koin.android.BuildConfig


fun logDebug(tag: String, value: String) {
    Log.d(tag, value)

}

fun logErrorDebug(tag: String, value: String, throwable: Throwable) {
    Log.e(tag, value, throwable)
}

fun ImageView.loadImageWithThumbnail(imageUrl: String, thumbnail: String?) {
    val options = RequestOptions()
        .centerCrop()

    if (thumbnail != null) {
        Glide.with(this.context)
            .setDefaultRequestOptions(options)
            .load(imageUrl)
            .thumbnail(Glide.with(this.context).load(thumbnail))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
    } else {
        Glide.with(this.context)
            .setDefaultRequestOptions(options)
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
    }
}

fun ImageView.loadCircularImage(uri: String) {
    val options = RequestOptions()
        .centerCrop()
        .circleCrop()
    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun Int.convertPixelsToDp(context: Context): Int {
    val resources = context.resources
    val metrics = resources.displayMetrics
    return this / (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}