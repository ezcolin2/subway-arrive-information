package com.example.xmlapi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.RatingBar
import android.widget.TextView
import com.example.xmlapi.databinding.FragmentCafeBinding

class CafeAdapter(val context: Context,val arr:ArrayList<Cafe>):BaseAdapter() {
    override fun getCount(): Int {
        return arr.size
    }

    override fun getItem(p0: Int): Any {
        return arr[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view :View = LayoutInflater.from(context).inflate(R.layout.list_cafe,null)
        val cafeName = view.findViewById<TextView>(R.id.txt_cafe)
        val cafeRatings = view.findViewById<RatingBar>(R.id.txt_rating)
        val cafeRatingsNum = view.findViewById<TextView>(R.id.txt_rating_num)
        val cafe = arr[position]
        cafeName.text = cafe.cafeName
        cafeRatingsNum.text = cafe.ratings.toString()
        cafeRatings.rating = cafe.ratings
        return view


    }
}