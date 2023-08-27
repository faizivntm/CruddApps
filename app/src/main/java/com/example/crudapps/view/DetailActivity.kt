package com.example.crudapps.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.crudapps.R
import com.example.crudapps.databinding.ActivityDetailBookBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val urlImage = intent.getStringExtra("urlImage")
        val judul = intent.getStringExtra("judul")
        val namaPenulis = intent.getStringExtra("namaPenulis")
        val kategori = intent.getStringExtra("kategori")
        val tahunTerbit = intent.getIntExtra("tahunTerbit", 0)

        Glide.with(this)
            .load(urlImage)
            .into(binding.coverBukuDetail)

        binding.judulBuku.text = judul
        binding.namaPenulis.text = namaPenulis
        binding.tahunTerbit.text = tahunTerbit.toString()
        binding.kategori.text = kategori

        binding.btnUpadte.setOnClickListener {
            //fungsi update
            btnUpdate()
        }

        binding.btnDelete.setOnClickListener {
            //fungsi delete
            btnDelete()
        }
    }
    fun btnUpdate(){
        // (menyimpan ke database)
        //?????????????
        val intent = Intent(this, UpdateActivity::class.java)
        startActivity(intent)

    }

    fun btnDelete(){
        //berikan instruksi untuk mendelete

    }
}