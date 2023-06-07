package com.cankarademir.travelapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cankarademir.travelapplication.R
import com.cankarademir.travelapplication.models.Travel

class RecyclerViewAdapter( var itemList: List<Travel>)  : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.txtTitle)
        val location = view.findViewById<TextView>(R.id.txtLocation)
        val note = view.findViewById<TextView>(R.id.txtNote)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.travel_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = itemList[position]

        holder.title.text = currentitem.title
        holder.location.text = currentitem.location
        holder.note.text = currentitem.note

        holder.itemView.setOnLongClickListener {
            if (position != RecyclerView.NO_POSITION) {
                longClickListener?.onItemLongClick(currentitem.id)
                true
            } else {
                false
            }
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(itemID: String)
    }

    private var longClickListener: OnItemLongClickListener? = null

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        longClickListener = listener
    }

}