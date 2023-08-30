package com.example.crudapps.ui.add

import android.R
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.crudapps.data.adapters.KategoriAdapter
import com.example.crudapps.databinding.ActivityAddBookBinding
import com.example.crudapps.models.bookModel
import com.example.crudapps.ui.detail.DetailViewModel
import com.example.crudapps.utils.Result
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBookBinding
    private lateinit var viewModel: AddViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(AddViewModel::class.java)


        setSpinnerItem()

        binding.simpanButtonAdd.setOnClickListener {
            // Memanggil fungsi simpan
            btnSimpan()
        }
    }

    private fun setSpinnerItem() {
        val spinnerItems = arrayOf("Novel", "Majalah", "Biografi", "Artikel")
        binding.katergoriAdd.adapter =
            KategoriAdapter(this, R.layout.simple_spinner_item, spinnerItems.asList())
    }

    private fun btnSimpan() {
        val urlImageCover = binding.urlkoverBuku.text.toString()
        val judulBuku = binding.namaBukuEditTextAdd.text.toString()
        val penulisBuku = binding.penulisBukuEditTextAdd.text.toString()
        val tahunTerbitBuku = binding.tahunTerbitEditTextAdd.text.toString()
        val kategori = binding.katergoriAdd.selectedItem.toString()

        // Validasi bahwa penting field tidak boleh kosong
        if (urlImageCover.isEmpty() || judulBuku.isEmpty() || penulisBuku.isEmpty()) {
            Toast.makeText(this, "Semua data harus diisi", Toast.LENGTH_SHORT).show()
            return
        }

        val addData = bookModel(
            urlImage = urlImageCover,
            judul = judulBuku,
            namaPenulis  = penulisBuku,
            tahunTerbit = tahunTerbitBuku.toIntOrNull() ?: 0, // Convert to Int or default to 0 if not valid
            kategori = kategori
        )

        // Mengamati hasil penambahan buku
        viewModel.addBook(addData).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    // Handle loading state if needed
                }

                is Result.Success -> {
                    Toast.makeText(this, result.data, Toast.LENGTH_SHORT).show()
                    finish()
                }

                is Result.Error -> {
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}


