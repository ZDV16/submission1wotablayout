package com.example.submission1.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1.databinding.ActivityMainBinding
import android.widget.SearchView
import com.example.submission1.GithubResponseItem
import com.example.submission1.model.MainViewModel
import com.example.submission1.adapter.UserAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    val viewModel by viewModels<MainViewModel>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val searchView = binding.search

        adapter = UserAdapter()
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GithubResponseItem) {
                val detail = Intent(this@MainActivity, DetailUserActivity::class.java)
                    .putExtra(DetailUserActivity.EXTRA_USER, data.login)
                    .putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    .putExtra(DetailUserActivity.EXTRA_AVATAR_URL, data.avatarUrl)
                startActivity(detail)
            }
        })

        adapter.notifyDataSetChanged()

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        searchView.setOnQueryTextListener(object : OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchUser()
                showRecyclerView()
                observableViewModel()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        binding.btnFavorite.setOnClickListener {
            val favPage = Intent(this@MainActivity, FavoriteActivity::class.java)
            startActivity(favPage)
        }

        showRecyclerView()
        observableViewModel()
        supportActionBar?.hide()
    }

    private fun showRecyclerView() {
        binding.rvListUser.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvListUser.setHasFixedSize(true)
        binding.rvListUser.adapter = adapter
    }

    private fun observableViewModel() {
        viewModel.itemsitem.observe(this) { users ->
            if (users != null) {
                adapter.setList(users)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun searchUser() {
        binding.apply {
            val query = search.query.toString()
            viewModel.URL = query
            viewModel.findUser()
        }
    }

}