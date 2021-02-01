package com.segura.imagesapp.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.segura.imagesapp.R
import com.segura.imagesapp.databinding.PhotoDetailFragmentBinding
import com.segura.imagesapp.domain.model.NetworkState
import com.segura.imagesapp.domain.model.photoDetail.PhotoDetailResponse
import com.segura.imagesapp.utils.createSnackBar
import com.segura.imagesapp.utils.loadCircularImage
import com.segura.imagesapp.utils.loadImageWithThumbnail
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PhotoDetailFragment : Fragment() {
    private val args: PhotoDetailFragmentArgs by navArgs()
    private lateinit var binding: PhotoDetailFragmentBinding



    private val viewModel: PhotoDetailViewModel by viewModel {
        parametersOf(
            args.imageId,
            args.profileId
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.photo_detail_fragment, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupObserver()


    }


    private fun setupObserver() {

        viewModel.imageInfo.observe(viewLifecycleOwner, {
            when (it.status) {
                NetworkState.RUNNING -> {
                    binding.progressImageDetail.visibility = View.VISIBLE
                }
                NetworkState.SUCCESS -> {
                    setDataToViews(it.data)
                    binding.progressImageDetail.visibility = View.GONE
                }
                NetworkState.FAILED -> {
                    binding.progressImageDetail.visibility = View.GONE
                    createSnackBar(binding.root, R.string.label_oops_an_error_happened)
                }
            }
        })
    }

    private fun setDataToViews(detailResponse: PhotoDetailResponse?) {
        detailResponse?.let { photoInfo ->
            binding.imgUserProfile.loadCircularImage(photoInfo.user.profileImage.medium)
            binding.txtUserName.text = photoInfo.user.name
            binding.txtUserBio.text = photoInfo.user.bio
            binding.imgContent.loadImageWithThumbnail(photoInfo.urls.full, photoInfo.urls.thumb)
            binding.txtImageDescription.text = photoInfo.altDescription
            binding.txtLikesCount.text = photoInfo.likes.toString()
            binding.txtViewsCount.text = photoInfo.views.toString()

            binding.imgUserProfile.setOnClickListener { view ->
                val action =
                    PhotoDetailFragmentDirections.actionPhotoDetailFragmentToProfileFragment(
                        photoInfo.user.username
                    )
                view.findNavController().navigate(action)
            }

            binding.txtUserName.setOnClickListener { view ->
                val action =
                    PhotoDetailFragmentDirections.actionPhotoDetailFragmentToProfileFragment(
                        photoInfo.user.username
                    )
                view.findNavController().navigate(action)
            }
        }
    }

}