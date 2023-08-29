package com.example.crudapps.ui

import android.R
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.crudapps.data.adapters.KategoriAdapter
import com.example.crudapps.databinding.ActivityAddBookBinding
import com.example.crudapps.models.AddModel

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

    private fun btnSimpan() {
        // Mengambil data dari elemen EditText
        val urlImageCover = binding.urlkoverBuku.text.toString()
        val judulBuku = binding.namaBukuEditTextAdd.text.toString()
        val penulisBuku = binding.penulisBukuEditTextAdd.text.toString()
        val tahunTerbitBuku = binding.tahunTerbitEditTextAdd.text.toString()
        val kategori = binding.katergoriAdd.selectedItem.toString()


        // Validasi bahwa semua field tidak boleh kosong
        if (urlImageCover.isEmpty() || judulBuku.isEmpty() || penulisBuku.isEmpty() || tahunTerbitBuku.isEmpty()) {
            Toast.makeText(this, "Semua data harus diisi", Toast.LENGTH_SHORT).show()
            return
        } else{
            // Membuat instance AddModel untuk menyimpan data buku
            val book = AddModel().apply {
                urlImage = urlImageCover
                judul = judulBuku
                namaPenulis = penulisBuku
                tahunTerbit = tahunTerbitBuku.toInt()
                this.kategori = kategori
            }

            // Memanggil fungsi addBook dari ViewModel untuk menyimpan data buku
            viewModel.addBook(book,
                onSuccess = {
                    // Penyimpanan berhasil, tampilkan toast berhasil
                    Toast.makeText(this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show()
                    // Bersihkan input fields
                    binding.urlkoverBuku.text.clear()
                    binding.namaBukuEditTextAdd.text.clear()
                    binding.penulisBukuEditTextAdd.text.clear()
                    binding.tahunTerbitEditTextAdd.text.clear()
                },
                onFailure = { e ->
                    // Penyimpanan gagal, tampilkan toast gagal
                    Toast.makeText(this, "Gagal menyimpan data: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            )
        }


    }

    private fun setSpinnerItem() {
        val spinnerItems = arrayOf("Novel", "Majalah", "Biografi", "Artikel")
        binding.katergoriAdd.adapter = KategoriAdapter(this, R.layout.simple_spinner_item, spinnerItems.asList())
    }
}
