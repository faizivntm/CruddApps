package com.example.crudapps.ui.update

import android.os.Bundle
import android.text.Editable
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.crudapps.R
import com.example.crudapps.data.adapters.KategoriAdapter
import com.example.crudapps.databinding.ActivityUpdateBinding
import com.example.crudapps.models.UpdateModel

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var viewModel: UpdateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(UpdateViewModel::class.java)

        setSpinnerItem()
        val intentData = getIntentData()

        setupUI(intentData)
        setupButton(intentData)
    }

    private fun setSpinnerItem() {
        val spinnerItems = arrayOf("Novel", "Majalah", "Biografi", "Artikel")
        binding.katergoriUpdate.adapter = KategoriAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, spinnerItems.asList())
    }

    private fun getIntentData(): UpdateModel {
        return UpdateModel(
            intent.getStringExtra("urlImage") ?: "",
            intent.getStringExtra("judul") ?: "",
            intent.getStringExtra("namaPenulis") ?: "",
            intent.getStringExtra("kategori") ?: "",
            intent.getIntExtra("tahunTerbit", 0)
        )
    }

    private fun setupUI(data: UpdateModel) {
        binding.urlCoverBukuUpdate.text = Editable.Factory.getInstance().newEditable(data.urlImage)
        binding.namaBukuUpdate.text = data.judul
        binding.penulisBukuUpdate.text = Editable.Factory.getInstance().newEditable(data.namaPenulis)
        binding.katergoriUpdate.setSelection(getIndex(binding.katergoriUpdate, data.kategori))
        binding.tahunTerbitUpdate.text = Editable.Factory.getInstance().newEditable(data.tahunTerbit.toString())
    }

    private fun setupButton(data: UpdateModel) {
        binding.simpanButtonUpdate.setOnClickListener {
            viewModel.updateBook(data, binding) { isSuccess ->
                if (isSuccess) {
                    handleSuccess()
                } else {
                    handleFailure(Exception("Pembaruan gagal"))
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

    private fun handleSuccess() {
        binding.urlCoverBukuUpdate.text.clear()
        binding.penulisBukuUpdate.text.clear()
        binding.tahunTerbitUpdate.text.clear()
        println("Pembaruan berhasil")
        Toast.makeText(this, "Pembaruan berhasil", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun handleFailure(exception: Exception) {
        println("Pembaruan gagal: ${exception.message}")
        Toast.makeText(this, "Pembaruan gagal: ${exception.message}", Toast.LENGTH_SHORT).show()
    }
}
