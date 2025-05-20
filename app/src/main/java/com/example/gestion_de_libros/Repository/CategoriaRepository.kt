package com.example.gestion_de_libros.Repository

import com.example.gestion_de_libros.Interfaces.*
import com.example.gestion_de_libros.Model.Categoria

class CategoriaRepository (private val api: ApiService){

    suspend fun fetchCategorias(token: String): List<Categoria> =
        api.getCategorias("Bearer $token").body() ?: emptyList()
    suspend fun fetchCategoria(id: Long, token: String): Categoria? =
        api.getCategoria(id, "Bearer $token").body()

    //método para crear una categoría
    suspend fun createCategoria(token: String, categoria: Categoria): Categoria? {
        val response = api.createCategoria("Bearer $token", categoria)
        return if (response.isSuccessful) response.body() else null
    }
}

