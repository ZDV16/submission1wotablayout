package com.example.submission1.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.example.submission1.R
import com.example.submission1.databinding.ActivityDetailUserBinding
import com.example.submission1.model.DetailViewModel
import com.example.submission1.adapter.SectionPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso

class DetailUserActivity : AppCompatActivity() {


    companion object {
        var user :String =""
        const val EXTRA_USER = "extra_username"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    private lateinit var binding: ActivityDetailUserBinding
    private val viewModel by viewModels<DetailViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnBack.setOnClickListener {
            val mainPage = Intent(this@DetailUserActivity, MainActivity::class.java)
            startActivity(mainPage)
        }
        val sectionPagerAdapter = SectionPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager){
            tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()


        val username = intent.getStringExtra(EXTRA_USER)
        user = username.toString()
        username?.let { viewModel.findDetail(it) }
        supportActionBar?.hide()

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