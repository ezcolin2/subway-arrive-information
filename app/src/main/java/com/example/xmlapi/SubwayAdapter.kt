package com.example.xmlapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class SubwayAdapter(val context:SubActivityGang, val subwayList:Array<Data>):BaseAdapter()
{

    override fun getCount(): Int {
        return subwayList.size
    }

    override fun getItem(position: Int): Any {
        return subwayList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position:Int,convertView:View?,parent:ViewGroup?): View {
        var view:View = LayoutInflater.from(context).inflate(R.layout.list_subway,null)
        val trainLineNm = view.findViewById<TextView>(R.id.heading)
        val arvlMsg2 = view.findViewById<TextView>(R.id.arrival)
        val recptnDt = view.findViewById<TextView>(R.id.time)
        val btrainNo = view.findViewById<TextView>(R.id.train_no)

        val subway = subwayList[position]
        trainLineNm.text=subway.trainLineNm
        arvlMsg2.text=subway.arvlMsg2
        recptnDt.text=subway.recptnDt
        btrainNo.text=subway.btrainNo
        return view

    }

}