package com.asparagus.ourlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asparagus.ourlist.R
import com.asparagus.ourlist.model.ToDoItem

class TaskAdapter(private val todoItems: List<ToDoItem>, private val onClickListener: OnClickListener)
    :RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.to_do_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskAdapter.ViewHolder, position: Int) {
        val tasks = todoItems[position]
        holder.rvTitle.text = tasks.title
    }

    override fun getItemCount(): Int {
        return todoItems.size
    }

    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val rvTitle: TextView = itemView.findViewById(R.id.todoTitle)
    }
}