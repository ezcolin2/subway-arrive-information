package com.example.xmlapi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.xmlapi.databinding.ListCommentBinding

class CommentAdapter(val storeComment: ArrayList<StoreComment>)
    : RecyclerView.Adapter<CommentAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListCommentBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(storeComment[position])
    }

    override fun getItemCount() = storeComment.size

    class Holder(private val binding: ListCommentBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(storeComment: StoreComment) {
            binding.writerComment.text = storeComment.comment
            binding.writerName.text = storeComment.user_name
            binding.writerTime.text = storeComment.time
            binding.writerRating.rating = storeComment.score
        }
    }
}