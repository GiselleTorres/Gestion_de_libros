package com.example.gestion_de_libros.Repository

import com.example.gestion_de_libros.Interfaces.*
import com.example.gestion_de_libros.Model.Libro
class LibroRepository (private val api: ApiService){

    suspend fun fetchLibros(token: String): List<Libro> =
        api.getLibros("Bearer $token").body() ?: emptyList()
    suspend fun fetchLibro(id: Long, token: String): Libro? =
        api.getLibro(id, "Bearer $token").body()

}












