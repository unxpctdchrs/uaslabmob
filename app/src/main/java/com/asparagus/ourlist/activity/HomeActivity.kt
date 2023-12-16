package com.asparagus.ourlist.activity

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.asparagus.ourlist.R
import com.asparagus.ourlist.adapter.ListAdapter
import com.asparagus.ourlist.databinding.ActivityHomeBinding
import com.asparagus.ourlist.model.List
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class HomeActivity : AppCompatActivity(), ListAdapter.ListAdapterInterface, ToDoActivity.OnDialogNextBtnClickListener {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var listDialog: Dialog

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var authId: String

    private lateinit var listAdapter: ListAdapter
    private lateinit var toDoItemList: ArrayList<List>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        authId = auth.currentUser!!.uid
        database = Firebase.database.reference.child("Tasks").child(authId)

        binding.listRecyclerView.setHasFixedSize(true)
        binding.listRecyclerView.layoutManager = LinearLayoutManager(this)

        toDoItemList = arrayListOf()
        listAdapter = ListAdapter(toDoItemList)
        listAdapter.setListener(this)
        binding.listRecyclerView.adapter = listAdapter

        getTaskFromFirebase()

        binding.addListBtn.setOnClickListener{
            listDialog = Dialog(this@HomeActivity)
            listDialog.setContentView(R.layout.activity_to_do)
            listDialog.window!!.setLayout(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            listDialog.show()
        }
    }

    private fun getTaskFromFirebase() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                toDoItemList.clear()
                for (taskSnapshot in snapshot.children) {
                    val todoTask = taskSnapshot.key?.let { List(it, taskSnapshot.value.toString()) }

                    if (todoTask != null) {
                        toDoItemList.add(todoTask)
                    }

                }
                Log.d("HomeActivity", "onDataChange: " + toDoItemList)
                listAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@HomeActivity, error.toString(), Toast.LENGTH_SHORT).show()
            }


        })
    }

    override fun onDeleteItemClicked(listData: List, position: Int) {
        listData.listId?.let {
            database.child(it).removeValue().addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onEditItemClicked(listData: List, position: Int) {
        TODO("Not yet implemented")
    }

    override fun saveTask(toDoTask: String, toDoEdit:EditText){
        database
            .push().setValue(toDoTask)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Task Added Successfully", Toast.LENGTH_SHORT).show()
                    toDoEdit.text = null

                } else {
                    Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        listDialog.dismiss()
    }

    override fun updateTask(listData: List, toDoEdit: EditText) {
//        val map = HashMap<String, Any>()
//        map[listData.listId] = listData.task
//        database.updateChildren(map).addOnCompleteListener {
//            if (it.isSuccessful) {
//                Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
//            }
//            listDialog.dismiss()
//        }
    }

}