package com.example.crudapps.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DeleteBook {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val booksRef: DatabaseReference = database.reference.child("books")

    fun deleteBookByJudul(judul: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        // Query untuk mencari buku dengan judul yang sesuai
        val query = booksRef.orderByChild("judul").equalTo(judul)

        // Melakukan query dan menghapus data
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val bookId = snapshot.children.first().key.toString()
                    val bookRef = booksRef.child(bookId)

                    bookRef.removeValue()
                        .addOnSuccessListener {
                            onSuccess()
                        }
                        .addOnFailureListener { e ->
                            onFailure(e)
                        }
                } else {
                    // Tidak ditemukan buku dengan judul yang sesuai
                    onFailure(Exception("Buku tidak ditemukan"))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                onFailure(Exception("Terjadi kesalahan saat mencari buku"))
            }
        })
    }
}
