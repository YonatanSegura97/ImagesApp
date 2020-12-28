package com.segura.imagesapp.utils

import android.view.View
import androidx.paging.PagedList
import com.google.android.material.snackbar.Snackbar
import com.segura.imagesapp.R

fun getPagingConfig() = PagedList.Config.Builder()
    .setPageSize(ConstantsUtils.ITEMS_BY_PAGE)
    .setEnablePlaceholders(false)
    .build()

fun createSnackBar(view: View, stringId: Int) {
    Snackbar.make(view, stringId, Snackbar.LENGTH_SHORT)
        .setBackgroundTint(view.context.getColor(R.color.light_blue))
        .show()

}