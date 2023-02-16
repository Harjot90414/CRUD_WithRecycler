package com.harjot.crud_withrecycler

import android.icu.text.Transliterator.Position
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapterClass(var array: ArrayList<UserModel>, var recyclerInterface: RecyclerInterface):
    RecyclerView.Adapter<RecyclerAdapterClass.ViewHolder>(){
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var tvName= view.findViewById<TextView>(R.id.tvName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.setText(array[position].name.toString())
        holder.tvName.setOnClickListener {
            recyclerInterface.click(position)
        }
    }
}