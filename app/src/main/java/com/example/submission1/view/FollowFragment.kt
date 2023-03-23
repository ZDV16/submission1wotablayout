package com.example.submission1.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1.GithubResponseItem
import com.example.submission1.MainViewModel

import com.example.submission1.databinding.FragmentFollowBinding


class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding
    private lateinit var detailViewModel: DetailViewModel
    val viewModel by viewModels<DetailViewModel>()
    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var position = 0
        var username = arguments?.getString(ARG_USERNAME)

        Log.d("arguments: position", position.toString())
        Log.d("arguments: username", username.toString())
        detailViewModel =
            ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        if (position == 1){
            showLoadingUser(true)
            username?.let { detailViewModel.getFollower(it) }
            detailViewModel.follower.observe(viewLifecycleOwner) {
                setFollowData(it)
                showLoadingUser(false)
            }
        } else {
           // showLoadingUser(true)
           // username.let { detailUserViewModel.getFollowing() }
           // detailUserViewModel.following.observe(viewLifecycleOwner){
          //      setFollowData(it)
           //     showLoadingUser(false)
            }
        }
    }

    private fun showLoadingUser(isLoading: Boolean){
        if (isLoading) {
            bind.loadingFollow.visibility = View.VISIBLE
        } else {
            binding.loadingFollow.visibility = View.GONE
        }
    }

    private fun setFollowData(listUser : List<GithubResponseItem>){
        binding.apply {
            binding.rv.layoutManager = LinearLayoutManager(requireActivity())
            val adapter = UserAdapter(listUser)
            binding.rvFollow.adapter = adapter
        }
}