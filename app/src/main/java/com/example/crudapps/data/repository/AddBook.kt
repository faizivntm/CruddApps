package com.example.crudapps.data.repository

import com.example.crudapps.models.bookModel
import com.google.firebase.database.FirebaseDatabase

object  AddBook {
    private val database = FirebaseDatabase.getInstance().reference

    fun addBook(book: bookModel, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val newBookRef = database.child("books").push()
        newBookRef.setValue(book)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }
}