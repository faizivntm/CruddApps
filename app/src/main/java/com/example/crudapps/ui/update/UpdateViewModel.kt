package com.example.crudapps.ui.update

import androidx.lifecycle.ViewModel
import com.example.crudapps.data.repository.BookRepository
import com.example.crudapps.models.bookModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class UpdateViewModel @Inject constructor(
    private val bookRepository: BookRepository
):ViewModel(){
    val updateResult = bookRepository.resultUpdateBook
    fun updateBook(judul: String, updateModel: bookModel) {
        bookRepository.updateBook(judul, updateModel)
    }
}
