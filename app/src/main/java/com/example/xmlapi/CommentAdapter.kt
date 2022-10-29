package com.example.xmlapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class CommentAdapter(val context: StoreActivity, val commentList:ArrayList<StoreComment>):
    BaseAdapter()
{

    override fun getCount(): Int {
        return commentList.size
    }

    override fun getItem(position: Int): Any {
        return commentList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position:Int, convertView: View?, parent: ViewGroup?): View {
        var view: View = LayoutInflater.from(context).inflate(R.layout.comment_list,null)

        val name = view.findViewById<TextView>(R.id.writer_name)
        val score = view.findViewById<TextView>(R.id.writer_score)
        val comment = view.findViewById<TextView>(R.id.writer_comment)
        val time = view.findViewById<TextView>(R.id.writer_time)
        val comments = commentList[position]
        name.text=comments.user_name
        score.text=comments.score.toString()
        comment.text=comments.comment
        time.text=comments.time
        return view

    }

}