package com.example.gestion_de_libros.Repository

import com.example.gestion_de_libros.Interfaces.*
import com.example.gestion_de_libros.Model.Usuario

class   UsuarioRepository (private val api: ApiService) {

    suspend fun fetchUsuarios(token: String): List<Usuario> =
        api.getUsuarios("Bearer $token").body() ?: emptyList()
    suspend fun fetchUsuario(id: Long, token: String): Usuario? =
        api.getUsuario(id, "Bearer $token").body()

    //método para crear un usuario
    suspend fun createUsuario(token: String, usuario: Usuario): Usuario? {
        val response = api.createUsuario("Bearer $token", usuario)
        return if (response.isSuccessful) response.body() else null
    }
}
