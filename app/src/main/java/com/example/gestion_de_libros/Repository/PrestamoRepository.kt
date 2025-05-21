package com.example.gestion_de_libros.Repository

import com.example.gestion_de_libros.Interfaces.*
import com.example.gestion_de_libros.Model.Prestamo

class PrestamoRepository (private val api: ApiService) {
    suspend fun fetchPrestamos(token: String): List<Prestamo> =
        api.getPrestamos("Bearer $token").body() ?: emptyList()
    suspend fun fetchPrestamo(id: Long, token: String): Prestamo? =
        api.getPrestamo(id, "Bearer $token").body()

    suspend fun createPrestamo(token: String, prestamo: Prestamo): Prestamo? {
        val response = api.createPrestamo("Bearer $token", prestamo)
        return if (response.isSuccessful) response.body() else null
    }

}




