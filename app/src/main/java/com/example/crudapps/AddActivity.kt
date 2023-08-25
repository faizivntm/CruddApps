package com.example.crudapps

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.crudapps.databinding.ActivityAddBinding

class AddActivity : Activity() {

    private lateinit var binding: ActivityAddBinding

    companion object {
        private const val REQUEST_IMAGE_PICK = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val simpanButton: Button = findViewById(R.id.simpanButton)

        setSpinnerItem()

        binding.coverImageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_IMAGE_PICK)
        }

        simpanButton.setOnClickListener {
            // Ambil data dari elemen EditText
           binding.namaBukuEditText.text.toString()
           binding.penulisBukuEditText.text.toString()

            // Lakukan sesuatu dengan data (misalnya, menyimpan ke database)
        }

    }

    fun setSpinnerItem(){
        val spinnerItems = arrayOf("Novel","Majalah","Biografi")
        binding.katergori.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerItems)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            val imageUri = data.data
            binding.coverImageView.setImageURI(imageUri)
        }
    }
}
