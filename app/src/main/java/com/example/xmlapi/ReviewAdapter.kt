package com.example.xmlapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.xmlapi.databinding.ListMyreviewBinding

class ReviewAdapter(val context: Review, val arr: Array<Review>): BaseAdapter() {
    override fun getCount(): Int {
        return arr.size
    }
//    : RecyclerView.Adapter<ReviewAdapter.Holder>() {

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
//        val binding = ListMyreviewBinding.inflate(LayoutInflater.from(parent.context))
//        return Holder(binding)
//    }
//
//    override fun onBindViewHolder(holder: Holder, position: Int) {
//        holder.bind(review[position])
//    }
//
//    override fun getItemCount() = review.size
//
//    class Holder(private val binding: ListMyreviewBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(review: Review){
//            review.name.also { binding.txtStorename = it }
//            binding.txtContents = review.contents
//        }
//    }

    override fun getItem(p0: Int): Any {
        return arr[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view : View = LayoutInflater.from(context).inflate(R.layout.fragment_myreview,null)
        val storename = view.findViewById<TextView>(R.id.txt_storename)
        val contents = view.findViewById<TextView>(R.id.txt_contents)

        val review = arr[position]
        storename.text = review.storename
        contents.text = review.contents
        return view


    }

}
