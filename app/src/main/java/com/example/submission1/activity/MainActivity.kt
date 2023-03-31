package com.example.submission1.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1.databinding.ActivityMainBinding
import android.widget.SearchView
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.submission1.GithubResponseItem
import com.example.submission1.model.MainViewModel
import com.example.submission1.adapter.UserAdapter
import com.example.submission1.setting.SettingPreferences
import com.example.submission1.setting.SettingViewModel
import com.example.submission1.setting.SettingViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    val viewModel by viewModels<MainViewModel>()
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

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

        binding.btnSettings.setOnClickListener {
            val settingPage = Intent(this@MainActivity, DarkModeActivity::class.java)
            startActivity(settingPage)
        }

        getSetting()
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

    private fun getSetting(){
        val pref = SettingPreferences.getInstance(dataStore)
        val mainViewModel =
            ViewModelProvider(this, SettingViewModelFactory(pref))[SettingViewModel::class.java]
        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
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