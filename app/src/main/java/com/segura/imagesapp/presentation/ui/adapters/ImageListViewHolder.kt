package com.segura.imagesapp.presentation.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import com.segura.imagesapp.databinding.PhotoListItemBinding
import com.segura.imagesapp.model.ImageItem
import com.segura.imagesapp.utils.loadCircularImage
import com.segura.imagesapp.utils.loadImageWithThumbnail

class ImageListViewHolder(
    private val binding: PhotoListItemBinding
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        callback: ImageListPagedAdapter.OnClickListener,
        photoItem: ImageItem?,
        position: Int
    ) {
        photoItem?.let { photoInfo ->

//            binding.imgContent.layoutParams.height = getRandomHeight()

            binding.imgContent.loadImageWithThumbnail(
                thumbnail = photoInfo.urls.thumb,
                imageUrl = photoInfo.urls.full
            )

            binding.imgUser.loadCircularImage(photoInfo.user.profileImage.medium)
            binding.txtLikesCount.text = photoInfo.likes.toString()
            binding.txtUserName.text = photoInfo.user.name

            binding.cardContainer.setOnClickListener {
                callback.onImageClicked(position, photoInfo)
            }

            binding.cardContainer.setOnLongClickListener {
                callback.onFavoriteClicked(position, photoInfo)
                return@setOnLongClickListener false
            }
            binding.imgUser.setOnClickListener {
                callback.onProfileClicked(position, photoInfo)
            }
            binding.txtUserName.setOnClickListener {
                callback.onProfileClicked(position, photoInfo)
            }
        }
    }

    private fun getRandomHeight(): Int {
        val maxDpSize = 800
        val minDpSize = 400
        return (minDpSize..maxDpSize).random()
    }
}