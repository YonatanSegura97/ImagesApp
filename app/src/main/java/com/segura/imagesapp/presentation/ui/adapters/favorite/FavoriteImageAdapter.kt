package com.segura.imagesapp.presentation.ui.adapters.favorite


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.segura.imagesapp.databinding.PhotoListItemBinding
import com.segura.imagesapp.domain.model.ImageItem
import com.segura.imagesapp.presentation.ui.adapters.ImageListViewHolder
import com.segura.imagesapp.presentation.ui.adapters.ImagePagedAdapter

class FavoriteImageAdapter(private val callBack: ImagePagedAdapter.OnClickListener) :
    ListAdapter<ImageItem, ImageListViewHolder>(diffCallback) {


    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ImageItem>() {
            override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val imageBinding = PhotoListItemBinding.inflate(inflater, parent, false)
        return ImageListViewHolder(imageBinding)
    }

    override fun onBindViewHolder(holder: ImageListViewHolder, position: Int) {
        holder.bind(callBack, getItem(position), position)
    }
}