package com.example.crudapps.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.crudapps.models.bookModel
import com.example.crudapps.utils.Result
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val db: FirebaseDatabase
) {
    private val resultGetBook = MediatorLiveData<Result<List<bookModel>>>()
    val resultUpdateBook = MutableLiveData<Result<String>>()
    val resultAddBook = MutableLiveData<Result<String>>()



    //getDataBook
    fun getBook(): LiveData<Result<List<bookModel>>> {
        val database = db.getReference("books")
        resultGetBook.value = Result.Loading
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dataList = mutableListOf<bookModel>()
                for (dataSnapshot in snapshot.children) {
                    val book = dataSnapshot.getValue(bookModel::class.java)
                    book?.let { dataList.add(it) }
                }
                resultGetBook.value = Result.Success(dataList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("EROR", "onCancelled: DatabaseEror")
                resultGetBook.value = Result.Error(error.message)
            }
        })
        return resultGetBook
    }

    fun updateBook(judul: String, updateModel: bookModel): LiveData<Result<String>> {
        resultUpdateBook.value = Result.Loading
        val query = db.reference.child("books").orderByChild("judul").equalTo(judul)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                    Log.i("apa hayo", "onDataChange: "+updateModel)

                query.addListenerForSingleValueEvent(object : ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.exists()){
                                    val bookId = snapshot.children.first().key.toString()
                                    val bookRef = db.reference.child("books").child(bookId)

                                    val updatedData = updateModel.toMap()


                                    bookRef.updateChildren(updatedData)
                                        .addOnSuccessListener {
                                            Log.i("update", "onDataChange: berhasil" + updateModel)
                                            resultUpdateBook.value = Result.Success("Berhasil")
                                        }
                                        .addOnFailureListener {
                                            resultUpdateBook.value = Result.Error(it.message.toString())
                                        }
                                }else{
                                    resultUpdateBook.value = Result.Error("Data tidak ditemukan")
                                }
                            }
                            override fun onCancelled(error: DatabaseError) {
                                resultUpdateBook.value = Result.Error("Terjadi kesalahan saat mencari buku")
                            }
                        })
            }

            override fun onCancelled(error: DatabaseError) {
                resultUpdateBook.value = Result.Error(error.message)
            }
        })

        return resultUpdateBook
    }

    fun addBook(addData: bookModel): LiveData<Result<String>> {
        // Mendapatkan referensi ke "books" di database
        val booksRef = db.getReference("books")

        // Mencari data buku dengan judul yang sama
        val query = booksRef.orderByChild("judul").equalTo(addData.judul)

        resultAddBook.postValue(Result.Loading)

        // Mengecek apakah judul buku sudah ada dalam database
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Judul buku sudah ada, kirim pesan error
                    resultAddBook.postValue(Result.Error("Judul buku sudah ada"))
                } else {
                    // Judul buku belum ada, tambahkan data buku ke database
                    val database = booksRef.push()
                    database.setValue(addData.toMap()) // Store data in the database

                    database.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            Log.i("Add", "onDataChange: Data berhasil ditambahkan")
                            resultAddBook.postValue(Result.Success("Data berhasil ditambahkan"))
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.i("Add", "onCancelled: Data gagal ditambahkan")
                            resultAddBook.postValue(Result.Error(error.message))
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Add", "onCancelled: Pengecekan judul buku dibatalkan")
                resultAddBook.postValue(Result.Error(error.message))
            }
        })

        return resultAddBook
    }


    fun detailBook(judul: String): LiveData<Result<bookModel>> {
        val database = FirebaseDatabase.getInstance().getReference("books").orderByChild("judul").equalTo(judul)

        val resultLiveData = MutableLiveData<Result<bookModel>>()

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val foundBooks = mutableListOf<bookModel>()
                for (dataSnapshot in snapshot.children) {
                    val book = dataSnapshot.getValue(bookModel::class.java)
                    book?.let { foundBooks.add(it) }
                }

                if (foundBooks.isNotEmpty()) {
                    resultLiveData.value = Result.Success(foundBooks[0])
                }
            }

            override fun onCancelled(error: DatabaseError) {
                resultLiveData.value = Result.Error(error.message)
            }
        })

        return resultLiveData
    }

    fun deleteBook(judul: String): LiveData<Result<String>> {
        // Referensi ke node "books" di database Firebase
        val resultLiveData = MutableLiveData<Result<String>>()
        val database = db.getReference("books").orderByChild("judul").equalTo(judul)
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Loop melalui setiap buku yang ditemukan
                for (bookSnapshot in snapshot.children) {
                    // Hapus buku dari database
                    bookSnapshot.ref.removeValue()
                }
                resultLiveData.value = Result.Success("Data berhasil dihapus")

            }

            override fun onCancelled(error: DatabaseError) {
                resultLiveData.value = Result.Success(error.message)

            }
        })
        return resultLiveData
    }




}
