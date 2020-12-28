package com.segura.imagesapp.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.segura.imagesapp.databinding.NetworkStateItemBinding
import com.segura.imagesapp.network.NetworkState

class NetworkStateViewHolder(private val binding: NetworkStateItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        networkState: NetworkState?, callback: ImageListPagedAdapter.OnClickListener
    ) {
        hideViews()
        setVisibleRightViews(networkState)
        binding.btnRetry.setOnClickListener { callback.onRetryClicked() }
    }

    private fun setVisibleRightViews(networkState: NetworkState?) {
        when (networkState) {
            NetworkState.FAILED -> {
                binding.btnRetry.visibility = View.VISIBLE
                binding.txtNetworkMessage.visibility = View.VISIBLE
            }
            NetworkState.RUNNING -> {
                binding.progressNetworkState.visibility = View.VISIBLE
            }
        }
    }

    private fun hideViews() {
        binding.btnRetry.visibility = View.GONE
        binding.txtNetworkMessage.visibility = View.GONE
        binding.progressNetworkState.visibility = View.GONE
    }
}