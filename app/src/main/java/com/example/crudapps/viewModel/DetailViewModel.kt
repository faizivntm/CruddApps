package com.example.crudapps.viewModel

import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.crudapps.firebase.DeleteBook
import com.example.crudapps.data.model.UpdateModel

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    var urlImage: String? = null
    var judul: String? = null
    var namaPenulis: String? = null
    var kategori: String? = null
    var tahunTerbit: Int = 0

    // Definisikan sebuah live data untuk mengirim perintah navigasi ke View (Activity)
    val navigateToUpdateActivity: MutableLiveData<Unit> = MutableLiveData()

    fun initDataFromIntent(intent: Intent) {
        urlImage = intent.getStringExtra("urlImage")
        judul = intent.getStringExtra("judul")
        namaPenulis = intent.getStringExtra("namaPenulis")
        kategori = intent.getStringExtra("kategori")
        tahunTerbit = intent.getIntExtra("tahunTerbit", 0)
    }

    fun btnUpdate() {
        // Mengirim perintah navigasi ke View (Activity)
        navigateToUpdateActivity.postValue(Unit)
    }

    fun btnDelete() {
        // Berikan instruksi untuk mendelete
        val deleteBookInstance = DeleteBook()

        deleteBookInstance.deleteBookByJudul(judul.toString(),
            onSuccess = {
                // Aksi jika penghapusan berhasil
                println("Penghapusan berhasil")
                Toast.makeText(getApplication(), "Penghapusan berhasil", Toast.LENGTH_SHORT).show()

            },
            onFailure = { e ->
                // Aksi jika penghapusan gagal
                println("Penghapusan gagal: ${e.message}")
                Toast.makeText(getApplication(), "Penghapusan gagal: ${e.message}", Toast.LENGTH_SHORT).show()

            }
        )
    }

    // Fungsi toMap untuk mengubah model menjadi map
    private fun UpdateModel.toMap(): Map<String, Any> {
        return mapOf(
            "urlImage" to urlImage,
            "judul" to judul,
            "namaPenulis" to namaPenulis,
            "kategori" to kategori,
            "tahunTerbit" to tahunTerbit
        )
    }
}
