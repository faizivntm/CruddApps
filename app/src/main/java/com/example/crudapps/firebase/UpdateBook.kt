package com.example.crudapps.firebase

import com.example.crudapps.data.model.UpdateModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UpdateBook {
    private val database = FirebaseDatabase.getInstance().reference

    fun updateBook(judulBuku: String, updateData: Map<String, Any>, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val booksRef = database.child("books")

        booksRef.orderByChild("judul").equalTo(judulBuku)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val bookId = snapshot.children.first().key.toString()
                        val bookRef = booksRef.child(bookId)

                        val currentData = snapshot.children.first().getValue() as Map<String, Any>
                        val updatedBookData = currentData.toMutableMap()
                        updatedBookData.putAll(updateData)

                        bookRef.updateChildren(updatedBookData)
                            .addOnSuccessListener { onSuccess() }
                            .addOnFailureListener { e -> onFailure(e) }
                    } else {
                        onFailure(Exception("Buku dengan judul $judulBuku tidak ditemukan"))
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    onFailure(Exception("Terjadi kesalahan saat mencari buku"))
                }
            })
    }


}