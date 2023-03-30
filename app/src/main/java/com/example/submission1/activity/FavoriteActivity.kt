package com.example.submission1.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1.GithubResponseItem
import com.example.submission1.adapter.UserAdapter
import com.example.submission1.databinding.ActivityFavoriteBinding
import com.example.submission1.model.FavoriteViewModel
import com.example.submission1.room.FavoriteUser

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    val viewModel by viewModels<FavoriteViewModel>()


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GithubResponseItem) {
                val detail = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
                    .putExtra(DetailUserActivity.EXTRA_USER, data.login)
                    .putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    .putExtra(DetailUserActivity.EXTRA_AVATAR_URL, data.avatarUrl)
                startActivity(detail)
            }
        })

        adapter.notifyDataSetChanged()
        showRecyclerView()
        observableViewModel()
    }

    private fun showRecyclerView() {
        binding.rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.adapter = adapter
    }

    private fun observableViewModel() {
        viewModel.getFavorite()?.observe(this, ) {
            if (it!=null){
                val list = mapList(it)
                adapter.setList(list)
            }
            }
        }

    private fun mapList(users: List<FavoriteUser>): ArrayList<GithubResponseItem> {
        val listFavorite = ArrayList<GithubResponseItem>()
        for (user in users){
            val userMapped = GithubResponseItem(
                user.id,
                user.login,
                "",
                user.avatarUrl
            )
            listFavorite.add(userMapped)
        }
    return listFavorite
    }
}