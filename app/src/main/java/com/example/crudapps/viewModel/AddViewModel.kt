package com.example.crudapps.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.crudapps.firebase.AddBook
import com.example.crudapps.data.model.AddModel

class AddViewModel(application: Application) : AndroidViewModel(application) {
    val addSuccess: MutableLiveData<Unit> = MutableLiveData()
    val addFailure: MutableLiveData<String> = MutableLiveData()

    fun addBook(book: AddModel) {
        AddBook.addBook(book,
            onSuccess = {
                addSuccess.postValue(Unit)
            },
            onFailure = { e ->
                addFailure.postValue("Gagal menyimpan data: ${e.message}")
            }
        )
    }
}
