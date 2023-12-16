package com.asparagus.ourlist.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.asparagus.ourlist.adapter.TaskAdapter
import com.asparagus.ourlist.databinding.ActivityHomeBinding
import com.asparagus.ourlist.fragment.NewToDoListFragment
import com.asparagus.ourlist.model.TaskViewModel
import com.asparagus.ourlist.model.ToDoItem

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var taskViewModel: TaskViewModel
    private var items: ArrayList<ToDoItem> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        binding.addNewTaskBtn.setOnClickListener{
            NewToDoListFragment(null).show(supportFragmentManager, "newTodoList")
        }

//        binding.taskRecyclerView.layoutManager = LinearLayoutManager(this)
//        binding.taskRecyclerView.setHasFixedSize(true)
//        binding.taskRecyclerView.adapter = TaskAdapter(items, object : TaskAdapter.OnClickListener{
//            override fun onItemClick(position: Int) {
//                NewToDoListFragment(null).show(supportFragmentManager, "newTodoList")
//            }
//        })

        taskViewModel.taskItems.observe(this){
            binding.taskRecyclerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = it?.let { it1 ->
                    TaskAdapter(it1, object : TaskAdapter.OnClickListener{
                        override fun onItemClick(position: Int) {
                            Toast.makeText(this@HomeActivity, "test click", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }
    }
}