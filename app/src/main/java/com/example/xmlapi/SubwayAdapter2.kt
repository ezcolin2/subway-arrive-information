package com.example.xmlapi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.xmlapi.databinding.ListSubwayBinding

    class SubwayAdapter2(val subway: Array<Data>)
        : RecyclerView.Adapter<SubwayAdapter2.Holder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val binding = ListSubwayBinding.inflate(LayoutInflater.from(parent.context))
            return Holder(binding)
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.bind(subway[position])
        }

        override fun getItemCount() = subway.size

        class Holder(private val binding: ListSubwayBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(subway: Data) {
                binding.arrival.text = subway.arvlMsg2
                binding.heading.text = subway.subwayHeading
                binding.time.text = subway.btrainNo
                binding.trainNo.text = subway.trainLineNm
            }
        }
    }
