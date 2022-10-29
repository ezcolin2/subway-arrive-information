package com.example.xmlapi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SubwayFragAdapter(val context: Context, val list:Array<Data>): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view:View
        val holder:ViewHolder
        if(convertView==null){
            //이거맞나
            view=LayoutInflater.from(context).inflate(R.layout.fragment_subway,null)
            holder=ViewHolder()
            holder.arvlMsg2 = view.findViewById(R.id.arrival)
            holder.subwayHeading = view.findViewById(R.id.heading)
            holder.btrainNo = view.findViewById(R.id.train_no)
            holder.recptnDt = view.findViewById(R.id.time)

            view.tag=holder
        } else{
            holder=convertView.tag as ViewHolder
            view=convertView
        }
        val item = list[position]
        //여기 하나 빠짐
        return view
    }

    override fun getItem(p0: Int): Any {
        return list.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }
    override fun getCount():Int{
        return list.size
    }
    private class ViewHolder{
        var trainLineNm:TextView?=null
        var arvlMsg2:TextView?=null
        var subwayHeading:TextView?=null
        var recptnDt:TextView?=null
        var btrainNo:TextView?=null
    }
}