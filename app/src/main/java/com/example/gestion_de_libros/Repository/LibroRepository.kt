package com.example.gestion_de_libros.Repository

import com.example.gestion_de_libros.Interfaces.*
import com.example.gestion_de_libros.Model.Libro
class LibroRepository (private val api: ApiService){

    suspend fun fetchLibros(token: String): List<Libro> =
        api.getLibros("Bearer $token").body() ?: emptyList()
    suspend fun fetchLibro(id: Long, token: String): Libro? =
        api.getLibro(id, "Bearer $token").body()

    // método para crear libros
    suspend fun createLibro(token: String, libro: Libro): Libro? {
        val response = api.createLibro("Bearer $token", libro)
        return if (response.isSuccessful) response.body() else null
    }

    // Método para actualizar un libro
    suspend fun updateLibro(token: String, libro: Libro): Libro? {
        val id = libro.idLibro ?: return null
        val response = api.updateLibro("Bearer $token", id, libro)
        return if (response.isSuccessful) response.body() else null
    }

    // Método para eliminar un libro
    suspend fun deleteLibro(token: String, id: Long): Boolean {
        val response = api.deleteLibro("Bearer $token", id)
        return response.isSuccessful
    }

}












