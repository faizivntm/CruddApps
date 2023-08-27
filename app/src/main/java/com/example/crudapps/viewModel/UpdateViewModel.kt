package com.example.crudapps.viewModel

import androidx.lifecycle.ViewModel
import com.example.crudapps.data.model.UpdateModel
import com.example.crudapps.firebase.UpdateBook

class UpdateViewModel : ViewModel() {

    fun updateBook(
        judul: String,
        updateData: Map<String, Any>,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val updateBookInstance = UpdateBook()

        updateBookInstance.updateBook(judul, updateData, onSuccess, onFailure)
    }

    // Fungsi toMap untuk mengubah model menjadi map
    fun toUpdateMap(updateModel: UpdateModel): Map<String, Any> {
        return mapOf(
            "urlImage" to updateModel.urlImage,
            "judul" to updateModel.judul,
            "namaPenulis" to updateModel.namaPenulis,
            "kategori" to updateModel.kategori,
            "tahunTerbit" to updateModel.tahunTerbit
        )
    }
}
