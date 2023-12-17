package com.asparagus.ourlist.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
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

        holder.rvItem.setOnClickListener{
            onClickListener.onItemClick(position)
        }

        holder.rvDelete.setOnClickListener{
            onClickListener.onDeleteClick(position)
        }

        holder.rvCheck.setOnClickListener{
            onClickListener.onCompleted(position)
        }

        // Check if the task is completed and apply strikethrough if needed
        if (tasks.isCompleted) {
            holder.rvTitle.paintFlags = holder.rvTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.rvItem.setOnClickListener(null)
            holder.rvCheck.setOnClickListener(null)
        } else {
            // Reset the paint flags if the task is not completed
            holder.rvTitle.paintFlags = holder.rvTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

    }

    override fun getItemCount(): Int {
        return todoItems.size
    }

    interface OnClickListener {
        fun onItemClick(position: Int)
        fun onDeleteClick(position: Int)
        fun onCompleted(position: Int)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val rvItem: ConstraintLayout = itemView.findViewById(R.id.todoItem)
        val rvTitle: TextView = itemView.findViewById(R.id.todoTitle)
        val rvCheck: ImageView = itemView.findViewById(R.id.checkTask)
        val rvDelete: ImageView = itemView.findViewById(R.id.deleteTask)
    }
}