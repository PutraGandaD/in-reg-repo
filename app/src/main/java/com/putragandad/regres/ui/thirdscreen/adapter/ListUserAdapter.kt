package com.putragandad.regres.ui.thirdscreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.putragandad.regres.core.data.source.remote.response.ListUserData
import com.putragandad.regres.databinding.ItemRvUserdataBinding

class ListUserAdapter(
    private val onItemClick: (ListUserData) -> Unit
) : PagingDataAdapter<ListUserData, ListUserAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(
        private val binding: ItemRvUserdataBinding,
        private val onItemClick: (ListUserData) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dataUser: ListUserData) {
            binding.apply {
                tvUserName.text = "${dataUser.firstName} ${dataUser.lastName}"
                tvUserEmail.text = dataUser.email

                Glide.with(root.context)
                    .load(dataUser.avatar)
                    .centerCrop()
                    .into(ivUser)
            }

            // Set the click listener here, calling the lambda function
            binding.root.setOnClickListener {
                onItemClick(dataUser)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRvUserdataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClick)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListUserData>() {
            override fun areItemsTheSame(oldItem: ListUserData, newItem: ListUserData): Boolean =
                oldItem.firstName == newItem.firstName

            override fun areContentsTheSame(oldItem: ListUserData, newItem: ListUserData): Boolean =
                oldItem == newItem
        }
    }
}
