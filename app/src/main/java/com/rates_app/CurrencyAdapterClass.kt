package com.rates_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CurrencyAdapterClass(private val dataList: ArrayList<CurrencyDataClass>) :
    RecyclerView.Adapter<CurrencyAdapterClass.ViewHolderClass>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderClass {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolderClass(itemView)
    }

    override fun onBindViewHolder(
        holder: ViewHolderClass,
        position: Int
    ) {
        val currentItem = dataList[position]
        holder.rvImage.setImageResource(currentItem.dataImage)
        holder.rvCode.text = currentItem.dataCode
        holder.rvRate.text = currentItem.dataRate
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rvImage: ImageView = itemView.findViewById(R.id.ivFlag)
        val rvCode: TextView = itemView.findViewById(R.id.tvFromCode)
        val rvRate: TextView = itemView.findViewById(R.id.tvRate)
    }
}