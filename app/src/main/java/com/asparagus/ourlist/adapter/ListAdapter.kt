package com.asparagus.ourlist.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asparagus.ourlist.databinding.ToDoItemBinding
import com.asparagus.ourlist.model.List

class ListAdapter(private val list: ArrayList<List>):RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var listener: ListAdapterInterface? = null

    fun setListener(listener: ListAdapterInterface){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder {
        val binding = ToDoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                binding.todoTask.text = this.list

                Log.d("ListAdapter", "onBindViewHolder: "+this)
//                binding.editTask.setOnClickListener {
//                    listener?.onEditItemClicked(this , position)
//                }
//
//                binding.deleteTask.setOnClickListener {
//                    listener?.onDeleteItemClicked(this , position)
//                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding: ToDoItemBinding): RecyclerView.ViewHolder(binding.root)

    interface ListAdapterInterface{
        fun onDeleteItemClicked(listData: List , position : Int)
        fun onEditItemClicked(listData: List , position: Int)
    }
}