package com.segura.imagesapp.presentation.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.segura.imagesapp.R
import com.segura.imagesapp.databinding.ProfileFragmentBinding
import com.segura.imagesapp.model.User
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

    private lateinit var binding: ProfileFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupObservers()

    }

    private fun setupObservers() {
        viewModel.user.observe(viewLifecycleOwner, {
            setDataToViews(it)
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