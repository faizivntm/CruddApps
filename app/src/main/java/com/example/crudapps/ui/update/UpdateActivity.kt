package com.example.crudapps.ui.update

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.crudapps.data.adapters.KategoriAdapter
import com.example.crudapps.databinding.ActivityUpdateBinding
import com.example.crudapps.models.bookModel
import com.example.crudapps.utils.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var viewModel: UpdateViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(UpdateViewModel::class.java)


        setSpinnerItem()
        val data = getData()

        setupUI(data)
        binding.simpanButtonUpdate.setOnClickListener {
            setupButton(data)
        }
    }

        private fun setSpinnerItem() {
            val spinnerItems = arrayOf("Novel", "Majalah", "Biografi", "Artikel")
            binding.katergoriUpdate.adapter = KategoriAdapter(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                spinnerItems.asList()
            )
        }

        private fun getData(): bookModel {
            return bookModel(
                intent.getStringExtra("urlImage"),
                intent.getStringExtra("judul"),
                intent.getStringExtra("namaPenulis"),
                intent.getIntExtra("tahunTerbit", 0),
                intent.getStringExtra("kategori")
            )
        }

        private fun setupUI(updateModel: bookModel) {
            binding.urlCoverBukuUpdate.text =
                Editable.Factory.getInstance().newEditable(updateModel.urlImage)
            binding.namaBukuUpdate.text = updateModel.judul
            binding.penulisBukuUpdate.text =
                Editable.Factory.getInstance().newEditable(updateModel.namaPenulis)
            binding.tahunTerbitUpdate.text =
                Editable.Factory.getInstance().newEditable(updateModel.tahunTerbit.toString())
            binding.katergoriUpdate.setSelection(
                getIndex(
                    binding.katergoriUpdate,
                    updateModel.kategori.toString()
                )
            )
        }

        private fun setupButton(data: bookModel) {
            val updatedData = bookModel(
                binding.urlCoverBukuUpdate.text.toString(),
                binding.namaBukuUpdate.text.toString(),
                binding.penulisBukuUpdate.text.toString(),
                binding.tahunTerbitUpdate.text.toString().toInt(),
                binding.katergoriUpdate.selectedItem.toString()
            )
            viewModel.updateBook(data.judul.toString(), updatedData)
            viewModel.updateResult.observe(this) {
                when (it) {
                    is Result.Success -> {
                        binding.urlCoverBukuUpdate.text.clear()
                        binding.penulisBukuUpdate.text.clear()
                        binding.tahunTerbitUpdate.text.clear()
                        Toast.makeText(this, "Pembaruan berhasil", Toast.LENGTH_SHORT).show()
                        binding.progressBar.visibility = View.GONE
                        finish()
                    }

                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.updateView.visibility = View.GONE
                    }

                    is Result.Error -> {
                        println("Pembaruan gagal: ${it.error}")
                        Toast.makeText(this, "Pembaruan gagal: ${it.error}", Toast.LENGTH_SHORT)
                            .show()
                        binding.progressBar.visibility = View.GONE

                    }
                }
            }
        }

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