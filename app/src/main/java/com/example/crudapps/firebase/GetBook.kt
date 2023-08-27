package com.example.crudapps.firebase

import com.example.crudapps.data.model.GetModel
import com.google.firebase.database.*

object GetBook {

    fun getDataFromFirebase(onSuccess: (List<GetModel>) -> Unit, onError: (Exception) -> Unit) {
        val database = FirebaseDatabase.getInstance().getReference("books")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dataList = mutableListOf<GetModel>()
                for (dataSnapshot in snapshot.children) {
                    val book = dataSnapshot.getValue(GetModel::class.java)
                    book?.let { dataList.add(it) }
                }
                onSuccess(dataList)
            }

            override fun onCancelled(error: DatabaseError) {
                onError(error.toException())
            }
        })
    }
}
