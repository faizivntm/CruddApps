package com.example.crudapps.view

import AddBook
import android.app.Activity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.crudapps.R
import com.example.crudapps.adapter.KategoriAdapter
import com.example.crudapps.databinding.ActivityAddBookBinding
import com.example.crudapps.model.AddModel

class AddActivity : Activity() {

    private lateinit var binding: ActivityAddBookBinding

    companion object {
        private const val REQUEST_IMAGE_PICK = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSpinnerItem()

        binding.simpanButtonAdd.setOnClickListener {
            //memanggil fungsi simpan
          btnSimpan()
        }

    }

    fun btnSimpan(){
        // Ambil data dari elemen EditText
        val urlImageCover = binding.urlkoverBuku.text.toString()
        val judulBuku = binding.namaBukuEditTextAdd.text.toString()
        val penulisBuku = binding.penulisBukuEditTextAdd.text.toString()
        val tahunTerbitBuku = binding.tahunTerbitEditTextAdd.text.toString()
        val kategori = binding.katergoriAdd.selectedItem.toString()

        // (menyimpan ke database)
        //addDataBuku
        val book = AddModel()
        book.urlImage = urlImageCover
        book.judul = judulBuku
        book.namaPenulis = penulisBuku
        book.tahunTerbit = tahunTerbitBuku.toInt()
        book.kategori = kategori

        AddBook.addBook(book,
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

        binding.urlkoverBuku.text.clear()
        binding.namaBukuEditTextAdd.text.clear()
        binding.penulisBukuEditTextAdd.text.clear()
        binding.tahunTerbitEditTextAdd.text.clear()

    }

    fun setSpinnerItem(){
        val spinnerItems = arrayOf("Novel","Majalah","Biografi","Artikel")
        binding.katergoriAdd.adapter = KategoriAdapter(this, android.R.layout.simple_spinner_item, spinnerItems.asList())
    }

}
