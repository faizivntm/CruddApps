package com.example.crudapps.ui.detail

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.crudapps.data.repository.DeleteBook

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    var urlImage: String? = null
    var judul: String? = null
    var namaPenulis: String? = null
    var kategori: String? = null
    var tahunTerbit: Int = 0

    private val _navigateToUpdateActivity = MutableLiveData<Boolean>()
    val navigateToUpdateActivity: LiveData<Boolean>
        get() = _navigateToUpdateActivity

    fun initDataFromIntent(intent: Intent) {
        urlImage = intent.getStringExtra("urlImage")
        judul = intent.getStringExtra("judul")
        namaPenulis = intent.getStringExtra("namaPenulis")
        kategori = intent.getStringExtra("kategori")
        tahunTerbit = intent.getIntExtra("tahunTerbit", 0)
    }

    fun btnUpdate() {
        // Mengirim sinyal untuk memulai UpdateActivity
        _navigateToUpdateActivity.postValue(true)
    }

    fun deleteBook(onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val judul: String = judul ?: return
        val deleteBookInstance = DeleteBook()

        deleteBookInstance.deleteBookByJudul(judul,
            onSuccess = {
                onSuccess.invoke()
            },
            onFailure = { e ->
                onFailure.invoke(e)
            }
        )
    }
}
