package com.example.crudapps.ui.detail

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.crudapps.databinding.ActivityDetailBookBinding
import com.example.crudapps.models.bookModel
import com.example.crudapps.ui.update.UpdateActivity
import com.example.crudapps.utils.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class DetailActivity: AppCompatActivity() {
    private lateinit var binding: ActivityDetailBookBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        val judulBuku = intent.getStringExtra("judul")
        var data = bookModel()

        judulBuku?.let {
            viewModel.detailBook(it).observe(this, { result ->
                when (result) {
                    is Result.Success -> {
                        data = result.data
                        setupUI(data)
                    }

                    is Result.Error -> {

                    }

                    is Result.Loading -> {

                    }
                }
            })
        }

        binding.btnUpadte.setOnClickListener {
            // Memanggil fungsi update
            val intent = Intent(this, UpdateActivity::class.java)
            intent.putExtra("urlImage", data.urlImage)
            intent.putExtra("judul", data.judul)
            intent.putExtra("namaPenulis", data.namaPenulis)
            intent.putExtra("kategori", data.kategori)
            intent.putExtra("tahunTerbit", data.tahunTerbit)
            startActivity(intent)
        }

        binding.btnDelete.setOnClickListener {
            judulBuku?.let {
                viewModel.deleteBook(it).observe(this, { result ->
                    when (result) {
                        is Result.Success -> {
                            Toast.makeText(this, "Buku berhasil dihapus", Toast.LENGTH_SHORT).show()
                            finish()
                        }

                        is Result.Error -> {

                        }

                        is Result.Loading -> {

                        }
                    }
                })
            }
        }
    }

    private fun setupUI(data: bookModel) {
        Glide.with(binding.root)
            .load(data.urlImage)
            .into(binding.coverBukuDetail)

        binding.judulBuku.text = data.judul
        binding.kategori.text = data.kategori
        binding.namaPenulis.text = data.namaPenulis
        binding.tahunTerbit.text = data.tahunTerbit.toString()
    }


}

