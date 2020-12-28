package com.segura.imagesapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.segura.imagesapp.R
import com.segura.imagesapp.databinding.FragmentHomeBinding
import com.segura.imagesapp.model.ImageItem
import com.segura.imagesapp.network.NetworkState
import com.segura.imagesapp.ui.adapters.ImageListPagedAdapter
import com.segura.imagesapp.utils.createSnackBar
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), ImageListPagedAdapter.OnClickListener {

    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var fragmentHomeBinding: FragmentHomeBinding
    private lateinit var imageListPagerAdapter: ImageListPagedAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)


        return fragmentHomeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecycler()
        setupObservers()
        setupActions()

    }

    private fun setupActions() {
        fragmentHomeBinding.btnSearch.setOnClickListener {
            val action = HomeFragmentDirections.actionNavigationHomeToSearchFragment()
            it.findNavController().navigate(action)
        }
    }

    private fun setupObservers() {
        homeViewModel.imageList?.observe(viewLifecycleOwner, {
            imageListPagerAdapter.submitList(it)
        })

        homeViewModel.initialNetworkState?.observe(viewLifecycleOwner, {
            initialNetworkState(it)
        })
    }

    private fun initialNetworkState(networkState: NetworkState?) {
        networkState?.let {
            when (it) {
                NetworkState.SUCCESS -> {
                    fragmentHomeBinding.progressbarHome.visibility = View.GONE
                }
                NetworkState.FAILED -> {
                    fragmentHomeBinding.progressbarHome.visibility = View.GONE
                }
                NetworkState.RUNNING -> {
                    fragmentHomeBinding.progressbarHome.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun prepareRecycler() {
        imageListPagerAdapter = ImageListPagedAdapter(this)
        val gridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        fragmentHomeBinding.recyclerImages.apply {
            adapter = imageListPagerAdapter
            layoutManager = gridLayoutManager
        }
    }

    override fun onImageClicked(position: Int, imageItem: ImageItem) {
        val action = HomeFragmentDirections.actionNavigationHomeToPhotoDetailFragment(
            imageId = imageItem.id,
            profileId = imageItem.user.id
        )
        fragmentHomeBinding.root.findNavController().navigate(action)
    }

    override fun onFavoriteClicked(position: Int, imageItem: ImageItem) {
        homeViewModel.insertFavoritePhoto(imageItem)
        createSnackBar(fragmentHomeBinding.root,R.string.label_add_favorites_message)
    }

    override fun onRetryClicked() {

    }

    override fun onRemoveFavorite(position: Int, imageItem: ImageItem) {

    }

    override fun onProfileClicked(position: Int, imageItem: ImageItem) {
        val action =
            HomeFragmentDirections.actionNavigationHomeToProfileFragment(imageItem.user.username)
        fragmentHomeBinding.root.findNavController().navigate(action)
    }
}