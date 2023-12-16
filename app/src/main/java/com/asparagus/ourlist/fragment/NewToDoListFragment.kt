package com.asparagus.ourlist.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.asparagus.ourlist.databinding.FragmentNewToDoListBinding
import com.asparagus.ourlist.model.TaskViewModel
import com.asparagus.ourlist.model.ToDoItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalTime

class NewToDoListFragment(var todoItem: ToDoItem?) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentNewToDoListBinding
    private lateinit var taskViewModel: TaskViewModel
    private var dueTime: LocalTime? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewToDoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        taskViewModel = ViewModelProvider(activity).get(TaskViewModel::class.java)

        binding.addBtn.setOnClickListener{
            val title = binding.etTitle.text.toString()
            val desc = binding.etDescription.text.toString()
            if(todoItem == null)
            {
                val newTask = ToDoItem(title, desc, dueTime,null)
                taskViewModel.addTaskItem(newTask)
            }
            else
            {
                taskViewModel.updateTaskItem(todoItem!!.id, title, desc, dueTime)
            }
            binding.etTitle.setText("")
            binding.etDescription.setText("")
            dismiss()
        }
    }
}