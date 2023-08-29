package com.example.crudapps.ui

import androidx.lifecycle.ViewModel
import com.example.crudapps.models.AddModel
import com.example.crudapps.data.firebases.AddBook

class AddViewModel : ViewModel() {

    fun addBook(book: AddModel, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        AddBook.addBook(book, onSuccess, onFailure)
    }
}
