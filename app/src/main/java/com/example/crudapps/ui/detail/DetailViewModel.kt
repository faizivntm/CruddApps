package com.example.crudapps.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.crudapps.data.repository.BookRepository
import com.example.crudapps.models.bookModel
import com.example.crudapps.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel

class DetailViewModel @Inject constructor(
    private val bookRepository: BookRepository
): ViewModel() {
    fun detailBook(judul: String): LiveData<Result<bookModel>> {

        return bookRepository.detailBook(judul)
    }
    fun deleteBook(judul: String): LiveData<Result<String>>{
        return bookRepository.deleteBook(judul)
    }
}
