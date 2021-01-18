package com.segura.imagesapp.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.segura.imagesapp.databinding.PhotoListItemBinding
import com.segura.imagesapp.model.ImageItem

class ImagePagedAdapter(private val callback: ImageListPagedAdapter.OnClickListener) :
    PagingDataAdapter<ImageItem, ImageListViewHolder>(ItemComparator) {


    object ItemComparator : DiffUtil.ItemCallback<ImageItem>() {
        override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: ImageListViewHolder, position: Int) {
        holder.bind(callback, photoItem = getItem(position), position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val photoBinding = PhotoListItemBinding.inflate(inflater, parent, false)
        return ImageListViewHolder(photoBinding)
    }
}