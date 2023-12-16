package com.asparagus.ourlist.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.asparagus.ourlist.R
import com.asparagus.ourlist.databinding.ActivityToDoBinding
import com.asparagus.ourlist.model.List
import com.google.android.material.textfield.TextInputEditText

class ToDoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityToDoBinding
    private var listData: List? = null
    private var listener : OnDialogNextBtnClickListener? = null

    fun setListener(listener: OnDialogNextBtnClickListener) {
        this.listener = listener
    }

    companion object {
        const val TASK_ID_EXTRA = "taskId"
        const val TASK_EXTRA = "task"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToDoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(TASK_ID_EXTRA) && intent.hasExtra(TASK_EXTRA)) {
            val taskId = intent.getStringExtra(TASK_ID_EXTRA)
            val task = intent.getStringExtra(TASK_EXTRA)

            listData = List(taskId, task)
            binding.etNewList.setText(listData?.list)
        }

        binding.addNewListBtn.setOnClickListener{
            Toast.makeText(this, "Click!", Toast.LENGTH_SHORT).show()
//            val todoTask = binding.etNewList.text.toString()
//            if (todoTask.isNotEmpty()){
//                if (listData == null){
//                    listener?.saveTask(todoTask , binding.etNewList)
//                }else{
//                    listData!!.list = todoTask
//                    listener?.updateTask(listData!!, binding.etNewList)
//                }
//
//            }
        }
    }

    interface OnDialogNextBtnClickListener{
        fun saveTask(toDoTask:String , toDoEdit: EditText)
        fun updateTask(listData: List , toDoEdit: EditText)
    }
}