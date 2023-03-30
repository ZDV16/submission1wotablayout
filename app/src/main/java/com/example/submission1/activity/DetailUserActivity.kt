package com.example.submission1.activity

import android.content.Intent
import android.nfc.NfcAdapter.EXTRA_ID
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_username"
        const val EXTRA_ID = "0"
        const val EXTRA_AVATAR_URL = "extra_avatar_url"
        var user: String = ""

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

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.btnBack.setOnClickListener {
            val mainPage = Intent(this@DetailUserActivity, MainActivity::class.java)
            startActivity(mainPage)
        }

        val sectionPagerAdapter = SectionPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        val username = intent.getStringExtra(EXTRA_USER)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR_URL)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        user = username.toString()
        username?.let { viewModel.findDetail(it) }


        if (username != null) {
            viewModel.findDetail(username)
            viewModel.userSelected.observe(this) { userDetail ->
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

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val check = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (check != null) {
                    if (check > 0) {
                        binding.toggleFav.isChecked = true
                        _isChecked = true
                    } else {
                        binding.toggleFav.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.toggleFav.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked) {
                if (username != null && avatarUrl != null) {
                    viewModel.addToFavorite(id, username, avatarUrl)
                }

            } else {
                viewModel.removeFromFavorite(id)
            }
            binding.toggleFav.isChecked = _isChecked
        }


        supportActionBar?.hide()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar4.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}