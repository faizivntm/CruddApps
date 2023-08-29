package com.example.crudapps.data.repository

import com.example.crudapps.models.AddModel
import com.google.firebase.database.FirebaseDatabase

object  AddBook {
    private val database = FirebaseDatabase.getInstance().reference

    fun addBook(book: AddModel, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val newBookRef = database.child("books").push()
        newBookRef.setValue(book)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }
}