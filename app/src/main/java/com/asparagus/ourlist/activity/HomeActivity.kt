package com.asparagus.ourlist.activity

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.asparagus.ourlist.adapter.TaskAdapter
import com.asparagus.ourlist.databinding.ActivityHomeBinding
import com.asparagus.ourlist.databinding.ToDoItemBinding
import com.asparagus.ourlist.fragment.NewToDoListFragment
import com.asparagus.ourlist.model.TaskViewModel
import com.asparagus.ourlist.model.ToDoItem

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        binding.addNewTaskBtn.setOnClickListener{
            NewToDoListFragment(null).show(supportFragmentManager, "newTodoList")
        }

        //tasks item listener
        taskViewModel.taskItems.observe(this){
            binding.taskRecyclerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = it?.let { it1 -> TaskAdapter(it1, object : TaskAdapter.OnClickListener {
                    override fun onItemClick(position: Int) {
                        Toast.makeText(this@HomeActivity, "test", Toast.LENGTH_SHORT).show()
                    }

                    override fun onDeleteClick(position: Int) {
                        val currentTask = taskViewModel.taskItems.value?.get(position)
                        taskViewModel.deleteTaskItem(currentTask!!.id)
                    }

                    override fun onCompleted(position: Int) {
                        val currentTask = taskViewModel.taskItems.value?.get(position)
                        // Check if the task is not null and not already completed
                        if (currentTask != null && !currentTask.isCompleted) {
                            currentTask.isCompleted = true
                            // Update the task in the ViewModel or repository
                            taskViewModel.updateTaskItem(currentTask.id, currentTask.title, currentTask.description, currentTask.dueTime, currentTask.isCompleted)

                            // Notify the observer to update the UI
                            taskViewModel.taskItems.postValue(taskViewModel.taskItems.value)
                        }
                    }
                }) }
            }
        }
    }
}