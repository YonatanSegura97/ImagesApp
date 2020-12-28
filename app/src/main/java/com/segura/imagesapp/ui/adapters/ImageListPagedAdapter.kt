package com.segura.imagesapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.segura.imagesapp.R
import com.segura.imagesapp.databinding.NetworkStateItemBinding
import com.segura.imagesapp.databinding.PhotoListItemBinding
import com.segura.imagesapp.model.ImageItem
import com.segura.imagesapp.network.NetworkState

class ImageListPagedAdapter(private val callback: OnClickListener) :
    PagedListAdapter<ImageItem, RecyclerView.ViewHolder>(diffCallback) {

    private var networkState: NetworkState? = null

    interface OnClickListener {
        fun onImageClicked(position: Int, imageItem: ImageItem)
        fun onFavoriteClicked(position: Int, imageItem: ImageItem)
        fun onRetryClicked()
        fun onRemoveFavorite(position: Int, imageItem: ImageItem)
        fun onProfileClicked(position: Int, imageItem: ImageItem)
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ImageItem>() {
            override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val photoBinding = PhotoListItemBinding.inflate(inflater, parent, false)
        val networkStateItemBinding = NetworkStateItemBinding.inflate(inflater, parent, false)
        return when (viewType) {
            R.layout.photo_list_item -> ImageListViewHolder(photoBinding)
            R.layout.network_state_item -> NetworkStateViewHolder(networkStateItemBinding)
            else -> {
                throw IllegalArgumentException("Unknown view type $viewType")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.photo_list_item -> (holder as ImageListViewHolder).bind(
                callback,
                getItem(position),
                position
            )
            R.layout.network_state_item -> (holder as NetworkStateViewHolder).bind(
                networkState,
                callback
            )
        }


    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.network_state_item
        } else {
            R.layout.photo_list_item
        }
    }

    //UTILS FUNCTIONS ----
    fun updateNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.SUCCESS
}