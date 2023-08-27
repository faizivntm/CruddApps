package com.example.crudapps.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crudapps.R
import com.example.crudapps.adapter.ListAdapter
import com.example.crudapps.databinding.ActivityDetailBookBinding
import com.example.crudapps.databinding.ActivityUpdateBinding
import com.example.crudapps.firebase.GetBook
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityUpdateBinding.inflate(layoutInflater)
            setContentView(binding.root)

            // Deklarasikan binding untuk elemen UI dari layout
            val urlKoverBukuUpdate = binding.urlCoverBukuUpdate
            val namaBukuUpdate = binding.namaBukuUpdate
            val penulisBukuUpdate = binding.penulisBukuUpdate
            val tahunTerbitUpdate = binding.tahunTerbitUpdate
            val katergoriUpdate = binding.katergoriUpdate
            val simpanButtonUpdate = binding.simpanButtonUpdate

            // Tambahkan fungsi-fungsi yang sesuai untuk elemen UI di sini
        }
    }
