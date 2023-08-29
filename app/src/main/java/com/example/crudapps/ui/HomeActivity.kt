package com.example.crudapps.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crudapps.R
import com.example.crudapps.data.repository.GetBook
import com.example.crudapps.databinding.ActivityHomeBinding
import com.example.crudapps.databinding.ActivityUpdateBinding
import com.example.crudapps.databinding.FragmentHomeBinding
import com.example.crudapps.firebases.adapters.ListAdapter
import com.example.crudapps.ui.add.AddActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kennyc.view.MultiStateView

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        recyclerView = findViewById(R.id.homeRecycleView)
        floatingActionButton = findViewById(R.id.floatingActionButton)

        database = FirebaseDatabase.getInstance().getReference("books")

        //  RecyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // TODO: Initialize and set up your RecyclerView adapter here
        val adapter = ListAdapter()
        recyclerView.adapter = adapter

        binding.multiStateView.viewState = MultiStateView.ViewState.LOADING
        GetBook.getDataFromFirebase(
            onSuccess = { dataList ->
                adapter.updateData(dataList)
                if (dataList.isEmpty()){
                    binding.multiStateView.viewState = MultiStateView.ViewState.EMPTY
                }else{
                    binding.multiStateView.viewState = MultiStateView.ViewState.CONTENT

                }
                        },
            onError = { error ->
                binding.multiStateView.viewState = MultiStateView.ViewState.ERROR
                //penanganan eror nantinya gmna ges?
            }
        )

        floatingActionButton.setOnClickListener {
            //pindah ke halaman AddActivity
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

    }

}
