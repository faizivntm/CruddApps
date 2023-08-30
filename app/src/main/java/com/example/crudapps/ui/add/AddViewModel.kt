package com.example.crudapps.ui.add

import androidx.lifecycle.ViewModel
import com.example.crudapps.data.repository.BookRepository
import com.example.crudapps.models.bookModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel

class AddViewModel @Inject constructor(
    private val bookRepository: BookRepository
): ViewModel(){
    val addResult = bookRepository.resultUpdateBook
    fun addBook(addData: bookModel) = bookRepository.addBook(addData)
}
