package com.example.crudapps.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crudapps.R
import com.example.crudapps.adapter.ListAdapter
import com.example.crudapps.firebase.GetBook
import com.example.crudapps.data.model.GetModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)

        recyclerView = findViewById(R.id.homeRecycleView)
        floatingActionButton = findViewById(R.id.floatingActionButton)

        database = FirebaseDatabase.getInstance().getReference("books")

        //  RecyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // TODO: Initialize and set up your RecyclerView adapter here
         val adapter = ListAdapter()
         recyclerView.adapter = adapter

        GetBook.getDataFromFirebase(
            onSuccess = { dataList ->
                adapter.updateData(dataList)
            },
            onError = { error ->
                // Handle error here
            }
        )

        floatingActionButton.setOnClickListener {
            //pindah ke halaman AddActivity
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }
}
