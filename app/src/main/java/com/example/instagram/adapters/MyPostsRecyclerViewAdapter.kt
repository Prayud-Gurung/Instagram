package com.example.instagram.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.databinding.MyPostRecyclerviewDesignBinding
import com.example.instagram.models.Post
import com.squareup.picasso.Picasso

class MyPostsRecyclerViewAdapter(var context: Context, var postList: ArrayList<Post>) : RecyclerView.Adapter<MyPostsRecyclerViewAdapter.ViewHolder>(){
    inner class ViewHolder(var binding: MyPostRecyclerviewDesignBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding=MyPostRecyclerviewDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPost = postList[position]
        holder.binding.postImage
        Picasso.get().load(currentPost.postUrl).into(holder.binding.postImage)
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}