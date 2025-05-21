package com.example.gestion_de_libros.Repository

import com.example.gestion_de_libros.Interfaces.ApiService
import com.example.gestion_de_libros.Interfaces.ApiService.LoginResponse
import com.example.gestion_de_libros.Interfaces.ApiService.LoginRequest

class LoginRepository(private val usuarioRepo: UsuarioRepository) {
    suspend fun login(email: String, password: String): Boolean {
        // Se asume que fetchUsuarios no requiere token para obtener lista
        val users = usuarioRepo.fetchUsuarios("")
        return users.any { it.email == email && it.nombre == password }
    }

    /** Registro no soportado en local */
    suspend fun register(email: String, password: String): Boolean = false
}