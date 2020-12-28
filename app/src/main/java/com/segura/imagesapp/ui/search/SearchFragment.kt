package com.segura.imagesapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.segura.imagesapp.R
import com.segura.imagesapp.databinding.SearchFragmentBinding
import com.segura.imagesapp.model.ImageItem
import com.segura.imagesapp.ui.adapters.ImageListPagedAdapter
import com.segura.imagesapp.ui.home.HomeFragmentDirections
import com.segura.imagesapp.utils.createSnackBar
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(), ImageListPagedAdapter.OnClickListener {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private val viewModel: SearchViewModel by viewModel()
    lateinit var binding: SearchFragmentBinding
    private lateinit var imageListPagerAdapter: ImageListPagedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.search_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        prepareRecycler()
        setupObservers()
        configureSearch()

    }

    private fun configureSearch() {

        binding.editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.updateQuery(binding.editText.text?.toString()!!)
            }

            true
        }
    }

    private fun prepareRecycler() {
        imageListPagerAdapter = ImageListPagedAdapter(this)
        val gridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        binding.recyclerSearch.apply {
            adapter = imageListPagerAdapter
            layoutManager = gridLayoutManager
        }
    }

    private fun setupObservers() {
        viewModel.imageList?.observe(viewLifecycleOwner, {
            imageListPagerAdapter.submitList(it)
        })
    }

    override fun onImageClicked(position: Int, imageItem: ImageItem) {
        val action = SearchFragmentDirections.actionSearchFragmentToPhotoDetailFragment(
            imageId = imageItem.id,
            profileId = imageItem.user.id
        )
        binding.root.findNavController().navigate(action)
    }

    override fun onFavoriteClicked(position: Int, imageItem: ImageItem) {
        viewModel.insertFavoritePhoto(imageItem)
        createSnackBar(binding.root,R.string.label_add_favorites_message)
    }

    override fun onRetryClicked() {

    }

    override fun onRemoveFavorite(position: Int, imageItem: ImageItem) {

    }

    override fun onProfileClicked(position: Int, imageItem: ImageItem) {
        val action = SearchFragmentDirections.actionSearchFragmentToProfileFragment(
            userName = imageItem.user.username
        )
        binding.root.findNavController().navigate(action)
    }

}