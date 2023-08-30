package com.example.crudapps.models


data class bookModel(
    val urlImage: String? = null,
    val judul: String? = null,
    val namaPenulis: String? = null,
    val tahunTerbit: Int? = null,
    val kategori: String? = null
)  {
    fun toMap(): Map<String, Any?> =
        mapOf(
            "urlImage" to urlImage,
            "judul" to judul,
            "namaPenulis" to namaPenulis,
            "tahunTerbit" to tahunTerbit,
            "kategori" to kategori
        )
}

