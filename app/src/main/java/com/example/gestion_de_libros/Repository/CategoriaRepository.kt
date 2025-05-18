package com.example.gestion_de_libros.Repository

import com.example.gestion_de_libros.Interfaces.*
import com.example.gestion_de_libros.Model.Categoria

class CategoriaRepository (private val api: ApiService){

    //Anterior
    /*suspend fun obtenerCategorias(): List<Categoria> {
        return RetrofitClient.apiService.obtenerCategorias()
    }

    suspend fun obtenerCategoria(id: Long): Categoria {
        return RetrofitClient.apiService.obtenerCategoria(id)
    }

    suspend fun guardarCategoria(categoria: Categoria): Categoria {
        return RetrofitClient.apiService.guardarCategoria(categoria)
    }

    suspend fun eliminarCategoria(id: Long) {
        RetrofitClient.apiService.eliminarCategoria(id)
    }*/

    suspend fun fetchCategorias(token: String): List<Categoria> =
        api.getCategorias("Bearer $token").body() ?: emptyList()
    suspend fun fetchCategoria(id: Long, token: String): Categoria? =
        api.getCategoria(id, "Bearer $token").body()
}

