package com.example.crudapps.ui.add

import androidx.lifecycle.ViewModel
import com.example.crudapps.models.AddModel
import com.example.crudapps.data.repository.AddBook

class AddViewModel : ViewModel() {

    // Fungsi untuk menambahkan buku
    fun addBook(book: AddModel, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        // Memanggil fungsi addBook dari repository (AddBook) dengan callback onSuccess dan onFailure
        AddBook.addBook(book, onSuccess, onFailure)
    }
}
