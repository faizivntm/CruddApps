package com.example.crudapps.ui

import android.os.Bundle
import android.text.Editable
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.crudapps.adapter.KategoriAdapter
import com.example.crudapps.databinding.ActivityUpdateBinding
import com.example.crudapps.data.model.UpdateModel
import com.example.crudapps.viewModel.UpdateViewModel

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var viewModel: UpdateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(UpdateViewModel::class.java)

        setSpinnerItem()

        // Mengambil data dari intent
        val urlImage = intent.getStringExtra("urlImage") ?: ""
        val judul = intent.getStringExtra("judul") ?: ""
        val namaPenulis = intent.getStringExtra("namaPenulis") ?: ""
        val kategori = intent.getStringExtra("kategori") ?: ""
        val tahunTerbitAwal = intent.getIntExtra("tahunTerbit", 0)

        binding.urlCoverBukuUpdate.text = Editable.Factory.getInstance().newEditable(urlImage)
        binding.namaBukuUpdate.text = judul
        binding.penulisBukuUpdate.text = Editable.Factory.getInstance().newEditable(namaPenulis)
        binding.katergoriUpdate.setSelection(getIndex(binding.katergoriUpdate, kategori))
        binding.tahunTerbitUpdate.text = Editable.Factory.getInstance().newEditable(tahunTerbitAwal.toString())

        binding.simpanButtonUpdate.setOnClickListener {
            // Memanggil fungsi simpan
            btnSimpan()
        }
    }

    private fun btnSimpan() {
        // Mengambil data dari elemen EditText
        val urlImage = binding.urlCoverBukuUpdate.text.toString()
        val judul = binding.namaBukuUpdate.text.toString()
        val namaPenulis = binding.penulisBukuUpdate.text.toString()
        val kategori = binding.katergoriUpdate.selectedItem.toString()
        val tahunTerbitAwal = binding.tahunTerbitUpdate.text.toString().toInt()

        // Menyimpan tahun terbit baru atau mempertahankan tahun terbit awal
        val tahunTerbitUpdate = if (tahunTerbitAwal != 0) {
            try {
                tahunTerbitAwal
            } catch (e: NumberFormatException) {
                println("Kesalahan konversi tahun terbit: ${e.message}")
                return
            }
        } else {
            tahunTerbitAwal
        }

        // Menyiapkan data pembaruan
        val updateData = UpdateModel(
            urlImage = urlImage,
            judul = judul,
            namaPenulis = namaPenulis,
            kategori = kategori,
            tahunTerbit = tahunTerbitUpdate
        )

        // Membuat instance UpdateViewModel dan melakukan pembaruan
        viewModel.updateBook(judul, viewModel.toUpdateMap(updateData),
            onSuccess = {
                // Aksi yang ingin dilakukan jika pembaruan berhasil
                // Bersihkan input fields
                binding.urlCoverBukuUpdate.text.clear()
                binding.penulisBukuUpdate.text.clear()
                binding.tahunTerbitUpdate.text.clear()
                println("Pembaruan berhasil")
                Toast.makeText(this, "Pembaruan berhasil", Toast.LENGTH_SHORT).show()
            },
            onFailure = { e ->
                // Aksi yang ingin dilakukan jika pembaruan gagal
                println("Pembaruan gagal: ${e.message}")
                Toast.makeText(this, "Pembaruan gagal: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun setSpinnerItem() {
        val spinnerItems = arrayOf("Novel", "Majalah", "Biografi", "Artikel")
        binding.katergoriUpdate.adapter = KategoriAdapter(this, android.R.layout.simple_spinner_item, spinnerItems.asList())
    }

    // Mendefinisikan fungsi getIndex di dalam kelas
    private fun getIndex(spinner: Spinner, value: String): Int {
        val adapter = spinner.adapter
        for (i in 0 until adapter.count) {
            if (adapter.getItem(i).toString() == value) {
                return i
            }
        }
        return 0 // Default index
    }
}
