package com.asparagus.ourlist.fragment

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.asparagus.ourlist.databinding.FragmentNewToDoListBinding
import com.asparagus.ourlist.model.TaskViewModel
import com.asparagus.ourlist.model.ToDoItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalTime
import java.util.UUID

class NewToDoListFragment(var todoItem: ToDoItem?) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentNewToDoListBinding
    private lateinit var taskViewModel: TaskViewModel

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

        val database = FirebaseDatabase.getInstance("https://ourlist-860be-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val tasksReference: DatabaseReference = database.getReference("tasks")

        taskViewModel = ViewModelProvider(activity).get(TaskViewModel::class.java)

        if (todoItem != null)
        {
            binding.tTitle.text = "Edit Task"
            val editable = Editable.Factory.getInstance()
            binding.etTitle.text = editable.newEditable(todoItem!!.title)
            binding.etDescription.text = editable.newEditable(todoItem!!.description)
            binding.addBtn.text = "Save"
        }
        else
        {
            binding.tTitle.text = "New Task"
        }

        binding.addBtn.setOnClickListener{
            val title = binding.etTitle.text.toString()
            val desc = binding.etDescription.text.toString()

            if(todoItem == null) {
                val newTask = ToDoItem(title, desc,false)
                taskViewModel.addTaskItem(newTask)
                val newtaskReference = tasksReference.push()
                newtaskReference.setValue(newTask)
            }
            else {
                val uuid: UUID = UUID.fromString(todoItem!!.id)
                taskViewModel.updateTaskItem(uuid, title, desc, false)
                // Update existing task
                tasksReference.child(uuid.toString()).setValue(ToDoItem(title, desc, false))
            }

            binding.etTitle.setText("")
            binding.etDescription.setText("")
            dismiss()
        }
    }
}