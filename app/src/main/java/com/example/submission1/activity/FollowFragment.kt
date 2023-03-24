package com.example.submission1.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1.adapter.UserAdapter

import com.example.submission1.databinding.FragmentFollowBinding
import com.example.submission1.model.FollowViewModel


class FollowFragment : Fragment() {

    private lateinit var adapter: UserAdapter
    var position: Int = 0
    var username: String =""
    private lateinit var binding: FragmentFollowBinding
    val viewModel by viewModels<FollowViewModel>()



    companion object {
    const val ARG_POSITION = "position"
    const val EXTRA_USER = "extra_username"

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
    viewModel.isLoading.observe(viewLifecycleOwner) {
    showLoading(it)
    }

    arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = DetailUserActivity.user
            adapter = UserAdapter()
    }
    if (position == 1){
       showRecyclerView()
       observableViewModelFollower()
        }
    else {
        showRecyclerView()
        observableViewModelFollowing()
        }
    }

    private fun showRecyclerView() {
        binding.rvList.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvList.setHasFixedSize(true)
        binding.rvList.adapter = adapter
    }

    private fun observableViewModelFollower(){
       viewModel.getFollowers(username)
       viewModel.followers.observe(viewLifecycleOwner){users ->

       if(users != null){
       adapter.setList(users)
            }
       }
    }

    private fun observableViewModelFollowing(){
        viewModel.getFollowing(username)
        viewModel.following.observe(viewLifecycleOwner){users ->
        if(users != null){
            adapter.setList(users)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar2.visibility = View.VISIBLE
        } else {
            binding.progressBar2.visibility = View.GONE
        }
    }
}