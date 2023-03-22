package com.example.submission1.view

import android.content.Intent
import android.os.Bundle
import android.text.style.TtsSpan.ARG_USERNAME
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1.GithubResponseItem
import com.example.submission1.R
import com.example.submission1.activity.DetailUserActivity
import com.example.submission1.databinding.FragmentFollowersBinding


class FollowersFragment : Fragment() {
    private lateinit var followFragmentBinding: FragmentFollowersBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        followFragmentBinding = FragmentFollowersBinding.inflate(layoutInflater, container, false)
        val followViewModel = ViewModelProvider(
        this, ViewModelProvider.NewInstanceFactory()
        ).get(FollowViewModel::class.java)

        return followFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val username = arguments?.getString(USERNAME)

        val followViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowViewModel::class.java)

        if (index == 1) {
            followViewModel.getFollowers(username!!)
        } else {
            followViewModel.getFollowing(username!!)
        }
    }



    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            followFragmentBinding.progressBar2.visibility = View.VISIBLE
        } else {
            followFragmentBinding.progressBar2.visibility = View.GONE
        }
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val USERNAME = "username"
    }
    }
