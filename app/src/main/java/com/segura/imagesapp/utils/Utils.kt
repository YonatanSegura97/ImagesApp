package com.segura.imagesapp.utils

import android.view.View
import androidx.annotation.StringRes
import androidx.paging.PagedList
import com.google.android.material.snackbar.Snackbar
import com.segura.imagesapp.R



fun createSnackBar(view: View, @StringRes stringId: Int) {
    Snackbar.make(view, stringId, Snackbar.LENGTH_SHORT)
        .setBackgroundTint(view.context.getColor(R.color.light_blue))
        .show()

}