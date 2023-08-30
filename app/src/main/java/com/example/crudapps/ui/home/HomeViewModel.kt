package com.example.crudapps.ui.home

import androidx.lifecycle.ViewModel
import com.example.crudapps.data.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val BookRepository: BookRepository
):ViewModel() {

    fun getBook() = BookRepository.getBook()
}