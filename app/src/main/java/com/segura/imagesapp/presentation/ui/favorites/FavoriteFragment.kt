package com.segura.imagesapp.presentation.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.segura.imagesapp.R
import com.segura.imagesapp.databinding.FragmentDashboardBinding
import com.segura.imagesapp.model.ImageItem
import com.segura.imagesapp.presentation.ui.adapters.ImageListPagedAdapter
import com.segura.imagesapp.presentation.ui.adapters.favorite.FavoritePhotosAdapter
import com.segura.imagesapp.utils.createSnackBar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment(), ImageListPagedAdapter.OnClickListener {

    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private lateinit var fragmentDashboardBinding: FragmentDashboardBinding
    lateinit var favoritePhotosAdapter: FavoritePhotosAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentDashboardBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)

        return fragmentDashboardBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        setupObservers()
        setupSearch()
    }

    private fun setupSearch() {
        fragmentDashboardBinding.edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                favoriteViewModel.getFilterImages(fragmentDashboardBinding.edtSearch.text?.toString()!!)
            }

            true
        }
    }

    private fun setupObservers() {

        viewLifecycleOwner.lifecycleScope.launch {
            favoriteViewModel.favoritePhotos.collectLatest {
                favoritePhotosAdapter.submitList(it)
                if (it.isEmpty()) {
                    fragmentDashboardBinding.linearEmptyView.visibility = View.VISIBLE
                } else {
                    fragmentDashboardBinding.linearEmptyView.visibility = View.GONE
                }
            }
        }


        favoriteViewModel.filterImages.observe(viewLifecycleOwner, {
            favoritePhotosAdapter.submitList(it)
        })
    }

    private fun prepareRecyclerView() {
        favoritePhotosAdapter = FavoritePhotosAdapter(this)
        val gridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        fragmentDashboardBinding.favoriteRecyclers.apply {
            adapter = favoritePhotosAdapter
            layoutManager = gridLayoutManager
        }
    }

    override fun onImageClicked(position: Int, imageItem: ImageItem) {
        val action = FavoriteFragmentDirections.actionNavigationDashboardToPhotoDetailFragment(
            imageItem.id,
            imageItem.user.id
        )
        fragmentDashboardBinding.root.findNavController().navigate(action)
    }

    override fun onFavoriteClicked(position: Int, imageItem: ImageItem) {
        favoriteViewModel.deleteFavoritePhoto(imageItem)
        createSnackBar(fragmentDashboardBinding.root, R.string.label_remove_favorites_message)
    }

    override fun onRetryClicked() {

    }

    override fun onRemoveFavorite(position: Int, imageItem: ImageItem) {

    }

    override fun onProfileClicked(position: Int, imageItem: ImageItem) {
        val action =
            FavoriteFragmentDirections.actionNavigationDashboardToProfileFragment(imageItem.user.username)
        fragmentDashboardBinding.root.findNavController().navigate(action)
    }
}