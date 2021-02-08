package com.segura.imagesapp.presentation.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import com.segura.imagesapp.databinding.PhotoListItemBinding
import com.segura.imagesapp.domain.model.ImageItem
import com.segura.imagesapp.utils.loadCircularImage
import com.segura.imagesapp.utils.loadImageWithThumbnail

class ImageListViewHolder(
    private val binding: PhotoListItemBinding
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        callback: ImagePagedAdapter.OnClickListener,
        photoItem: ImageItem?,
        position: Int
    ) {
        photoItem?.let { photoInfo ->

//            binding.imgContent.layoutParams.height = getRandomHeight()

            binding.imgContent.transitionName = photoInfo.urls.regular
            binding.imgContent.loadImageWithThumbnail(
                thumbnail = photoInfo.urls.thumb,
                imageUrl = photoInfo.urls.small
            )

            binding.imgUser.loadCircularImage(photoInfo.user.profileImage.medium)
            binding.txtLikesCount.text = photoInfo.likes.toString()
            binding.txtUserName.text = photoInfo.user.name

            binding.cardContainer.setOnClickListener {
                callback.onImageClicked(position, photoInfo, binding.imgContent)
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

}