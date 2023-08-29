package com.example.crudapps.ui.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.crudapps.databinding.ActivityDetailBookBinding
import com.example.crudapps.ui.update.UpdateActivity

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBookBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.initDataFromIntent(intent)

        // Menampilkan data ke elemen UI
        Glide.with(this)
            .load(viewModel.urlImage)
            .into(binding.coverBukuDetail)

        binding.judulBuku.text = viewModel.judul
        binding.namaPenulis.text = viewModel.namaPenulis
        binding.tahunTerbit.text = viewModel.tahunTerbit.toString()
        binding.kategori.text = viewModel.kategori

        binding.btnUpadte.setOnClickListener {
            // Memanggil fungsi update
            viewModel.btnUpdate()
            finish()
        }

        binding.btnDelete.setOnClickListener {
            viewModel.deleteBook(
                onSuccess = {
                    // Aksi jika penghapusan berhasil
                    Toast.makeText(applicationContext, "Penghapusan berhasil", Toast.LENGTH_SHORT).show()
                    finish()
                },
                onFailure = { e ->
                    // Aksi jika penghapusan gagal
                    Toast.makeText(applicationContext, "Penghapusan gagal: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            )
        }

        viewModel.navigateToUpdateActivity.observe(this, Observer {
            val intent = Intent(this, UpdateActivity::class.java)
            intent.putExtra("urlImage", viewModel.urlImage)
            intent.putExtra("judul", viewModel.judul)
            intent.putExtra("namaPenulis", viewModel.namaPenulis)
            intent.putExtra("kategori", viewModel.kategori)
            intent.putExtra("tahunTerbit", viewModel.tahunTerbit)
            startActivity(intent)
        })
    }
}
