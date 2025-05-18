package com.example.gestion_de_libros.Repository

import com.example.gestion_de_libros.Interfaces.*
import com.example.gestion_de_libros.Model.Prestamo

class PrestamoRepository (private val api: ApiService) {
    suspend fun fetchPrestamos(token: String): List<Prestamo> =
        api.getPrestamos("Bearer $token").body() ?: emptyList()
    suspend fun fetchPrestamo(id: Long, token: String): Prestamo? =
        api.getPrestamo(id, "Bearer $token").body()

    //Anterior
    /*suspend fun obtenerPrestamos(): List<Prestamo> {
        return RetrofitClient.apiService.obtenerPrestamos()
    }

    suspend fun obtenerPrestamo(id: Long): Prestamo {
        return RetrofitClient.apiService.obtenerPrestamo(id)
    }

    suspend fun guardarPrestamo(prestamo: Prestamo): Prestamo {
        return RetrofitClient.apiService.guardarPrestamo(prestamo)
    }

    suspend fun eliminarPrestamo(id: Long) {
        RetrofitClient.apiService.eliminarPrestamo(id)
    }*/
}




