package com.example.gestion_de_libros.Repository

import com.example.gestion_de_libros.Interfaces.*
import com.example.gestion_de_libros.Model.Libro
class LibroRepository (private val api: ApiService){

    suspend fun fetchLibros(token: String): List<Libro> =
        api.getLibros("Bearer $token").body() ?: emptyList()
    suspend fun fetchLibro(id: Long, token: String): Libro? =
        api.getLibro(id, "Bearer $token").body()

//Anterior
   /* suspend fun obtenerLibros(): List<Libro> {
        return RetrofitClient.apiService.obtenerLibros()
    }

    suspend fun obtenerLibro(id: Libro): Libro {
        return RetrofitClient.apiService.obtenerLibro(id)
    }

    suspend fun guardarLibro(libro: Libro): Libro {
        return RetrofitClient.apiService.guardarLibro(libro)
    }

    suspend fun eliminarLibro(id: Long) {
        RetrofitClient.apiService.eliminarLibro(id)
    } */
}












