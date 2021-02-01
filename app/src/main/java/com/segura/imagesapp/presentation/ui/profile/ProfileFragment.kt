package com.segura.imagesapp.presentation.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.segura.imagesapp.R
import com.segura.imagesapp.databinding.FragmentProfileBinding

import com.segura.imagesapp.domain.model.NetworkState
import com.segura.imagesapp.domain.model.User
import com.segura.imagesapp.utils.createSnackBar
import com.segura.imagesapp.utils.loadCircularImage
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private val args: ProfileFragmentArgs by navArgs()
    private val viewModel: ProfileViewModel by viewModel {
        parametersOf(args.userName)
    }

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupObservers()

    }

    private fun setupObservers() {
        viewModel.user.observe(viewLifecycleOwner, {
            when (it.status) {
                NetworkState.RUNNING -> {
                    binding.progressProfile.visibility = View.VISIBLE
                }
                NetworkState.SUCCESS -> {
                    setDataToViews(it.data)
                    binding.progressProfile.visibility = View.GONE
                }
                NetworkState.FAILED -> {
                    binding.progressProfile.visibility = View.GONE
                    createSnackBar(binding.root, R.string.label_oops_an_error_happened)
                }
            }

        })
    }

    private fun setDataToViews(user: User?) {
        user?.let {
            binding.imagProfile.loadCircularImage(user.profileImage.large)
            binding.txtUserName.text = user.name
            binding.txtUserBio.text = user.bio
            binding.txtCollectionsCount.text = user.totalCollections.toString()
            binding.txtLikesCount.text = user.totalLikes.toString()
            binding.txtPhotosCount.text = user.totalPhotos.toString()
            binding.txtLocationName.text = user.location
        }
    }

}