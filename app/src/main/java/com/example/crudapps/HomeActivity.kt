package com.example.crudapps

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var floatingActionButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)

        recyclerView = findViewById(R.id.homeRecycleView)
        floatingActionButton = findViewById(R.id.floatingActionButton)

        // Set up RecyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // TODO: Initialize and set up your RecyclerView adapter here
        // val adapter = YourRecyclerViewAdapter()
        // recyclerView.adapter = adapter

        floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
            // Handle floating action button click here
            // For example, you can open a dialog or start another activity
        }
    }
}
