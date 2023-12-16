package com.asparagus.ourlist.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.LocalTime
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

    fun updateTaskItem(id: UUID, title: String, desc: String, dueTime: LocalTime?)
    {
        val list = taskItems.value
        val task = list!!.find { it.id == id }!!
        task.title = title
        task.description = desc
        task.dueTime = dueTime
        taskItems.postValue(list)
    }

    fun setCompleted(taskItem: ToDoItem)
    {
        val list = taskItems.value
        val task = list!!.find { it.id == taskItem.id }!!
        if (task.completedDate == null)
            task.completedDate = LocalDate.now()
        taskItems.postValue(list)
    }
}