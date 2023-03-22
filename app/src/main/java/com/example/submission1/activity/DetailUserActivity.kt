package com.example.submission1.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.submission1.databinding.ActivityDetailUserBinding
import com.example.submission1.view.DetailViewModel
import com.example.submission1.view.SectionPagerAdapter
import com.example.submission1.view.UserAdapter
import com.squareup.picasso.Picasso

class DetailUserActivity : AppCompatActivity() {

    private lateinit var adapter: SectionPagerAdapter
    companion object {
        const val EXTRA_USER = "extra_username"
    }

    private lateinit var binding: ActivityDetailUserBinding
    val viewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val username = intent.getStringExtra(EXTRA_USER)
        username?.let { viewModel.findDetail(it) }


        if (username != null) {
            viewModel.findDetail(username)
            viewModel.userSelected.observe(this) {userDetail ->
                if (userDetail != null) {
                    binding.apply {
                        tvNama.text = userDetail.name as CharSequence?
                        tvUsername.text = userDetail.login
                        tvFollowers.text = "${userDetail.followers} Followers"
                        tvFollowing.text = "${userDetail.following} Following"
                        Picasso.get()
                        .load(userDetail.avatarUrl)
                        .into(ivDetailUser)
                    }
                }
            }
        }
    }

}