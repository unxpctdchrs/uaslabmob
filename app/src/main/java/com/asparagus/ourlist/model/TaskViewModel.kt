package com.asparagus.ourlist.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.UUID

class TaskViewModel: ViewModel() {
    var taskItems = MutableLiveData<MutableList<ToDoItem>?>()

    init {
        taskItems.value = mutableListOf()
    }

    fun addTaskItem(newTask: ToDoItem)
    {
        val list = taskItems.value
        list!!.add(newTask)
        taskItems.postValue(list)
    }

    fun updateTaskItem(id: UUID, title: String, desc: String, isCompleted: Boolean)
    {
        val list = taskItems.value
        val task = list!!.find { it.id == id }!!
        task.title = title
        task.description = desc
        task.isCompleted = isCompleted
        taskItems.postValue(list)
    }

    fun deleteTaskItem(id: UUID){
        val list = taskItems.value
        list?.removeIf{it.id == id}
        taskItems.postValue(list)
    }
}