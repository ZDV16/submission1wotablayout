package com.example.submission1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.submission1.GithubResponseItem
import com.example.submission1.databinding.ItemUserBinding
import com.squareup.picasso.Picasso

class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private val listUser = ArrayList<GithubResponseItem>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(users: ArrayList<GithubResponseItem>) {
        listUser.clear()
        listUser.addAll(users)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: GithubResponseItem) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }

            binding.apply {
                tvItemName.text = user.login
                tvItemDescription.text = user.htmlUrl
                Picasso.get()
                    .load(user.avatarUrl)
                    .into(imgItemPhoto)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder((view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: GithubResponseItem)
    }

}