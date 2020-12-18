package com.synergia.datapoc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_data.view.*


class MsgLogAdapter(private val msgList: ArrayList<MessageLog>) : RecyclerView.Adapter<MsgLogAdapter.MsgLogViewHolder>() {

    class MsgLogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val msgFrom: TextView = itemView.tvFrom
        val msgDate: TextView = itemView.tvDate
        val msg: TextView = itemView.tvMsg
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MsgLogViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_data, parent, false)
        return MsgLogViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MsgLogViewHolder, position: Int) {
        val msg = msgList[position]
        holder.msgDate.text =msg.date
        holder.msgFrom.text =msg.sender
        holder.msg.text =msg.msg
    }

    override fun getItemCount(): Int = msgList.size
}