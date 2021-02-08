package com.segura.imagesapp.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.segura.imagesapp.R
import com.segura.imagesapp.databinding.FragmentHomeBinding
import com.segura.imagesapp.domain.model.ImageItem
import com.segura.imagesapp.presentation.ui.adapters.ImagePagedAdapter
import com.segura.imagesapp.utils.createSnackBar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), ImagePagedAdapter.OnClickListener {

    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var fragmentHomeBinding: FragmentHomeBinding
    private lateinit var imageListPagerAdapter: ImagePagedAdapter

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
//        homeViewModel.imageList?.observe(viewLifecycleOwner, {
//            imageListPagerAdapter.submitList(it)
//        })
//
//        homeViewModel.initialNetworkState?.observe(viewLifecycleOwner, {
//            initialNetworkState(it)
//        })

        fragmentHomeBinding.progressbarHome.visibility = View.INVISIBLE

        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.images.collectLatest {
                imageListPagerAdapter.submitData(it)

            }
        }

    }


    private fun prepareRecycler() {
        imageListPagerAdapter = ImagePagedAdapter(this)
        val gridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        fragmentHomeBinding.recyclerImages.apply {
            adapter = imageListPagerAdapter
            layoutManager = gridLayoutManager
        }
    }

    override fun onImageClicked(position: Int, imageItem: ImageItem, imageView: ImageView) {
        val action = HomeFragmentDirections.actionNavigationHomeToPhotoDetailFragment(
            imageId = imageItem.id,
            profileId = imageItem.user.id,
            imageUri = imageItem.urls.regular
        )
        val extras = FragmentNavigatorExtras(imageView to imageItem.urls.regular)

        fragmentHomeBinding.root.findNavController().navigate(action, extras)
    }

    override fun onFavoriteClicked(position: Int, imageItem: ImageItem) {
        homeViewModel.insertFavoritePhoto(imageItem)
        createSnackBar(fragmentHomeBinding.root, R.string.label_add_favorites_message)
    }

    override fun onRetryClicked() {
        //        Not implemented yet
    }


    override fun onProfileClicked(position: Int, imageItem: ImageItem) {
        val action =
            HomeFragmentDirections.actionNavigationHomeToProfileFragment(imageItem.user.username)
        fragmentHomeBinding.root.findNavController().navigate(action)
    }
}