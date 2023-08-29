package com.example.crudapps.ui.update

import androidx.lifecycle.ViewModel
import com.example.crudapps.models.UpdateModel
import com.example.crudapps.data.repository.UpdateBook
import com.example.crudapps.databinding.ActivityUpdateBinding

class UpdateViewModel : ViewModel() {

    private val updateBookInstance = UpdateBook()

    fun updateBook(
        updateModel: UpdateModel,
        binding: ActivityUpdateBinding,
        callback: (Boolean) -> Unit
    ) {
        val updateData = toUpdateMap(updateModel)

        updateBookInstance.updateBook(updateModel.judul, updateData,
            onSuccess = {
                callback(true)
            },
            onFailure = { e ->
                callback(false)
            }
        )
    }

    private fun toUpdateMap(updateModel: UpdateModel): Map<String, Any> {
        return mapOf(
            "urlImage" to updateModel.urlImage,
            "judul" to updateModel.judul,
            "namaPenulis" to updateModel.namaPenulis,
            "kategori" to updateModel.kategori,
            "tahunTerbit" to updateModel.tahunTerbit
        )
    }
}
