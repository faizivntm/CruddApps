package com.example.crudapps.firebase

import com.example.crudapps.data.model.AddModel
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