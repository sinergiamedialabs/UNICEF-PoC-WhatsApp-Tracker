package com.synergia.datapoc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_data.view.*


class SendLogAdapter(private val sendLog: ArrayList<SendLog>) : RecyclerView.Adapter<SendLogAdapter.MsgLogViewHolder>() {

    class MsgLogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val msgDate: TextView = itemView.tvDate
        val msg: TextView = itemView.tvMsg
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MsgLogViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_senddata, parent, false)
        return MsgLogViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MsgLogViewHolder, position: Int) {
        val msg = sendLog[position]
        holder.msgDate.text =msg.date
        holder.msg.text =msg.msg
    }

    override fun getItemCount(): Int = sendLog.size
}