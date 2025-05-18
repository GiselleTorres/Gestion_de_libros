package com.example.gestion_de_libros.Repository

import com.example.gestion_de_libros.Interfaces.*
import com.example.gestion_de_libros.Model.Usuario

class   UsuarioRepository (private val api: ApiService) {

    suspend fun fetchUsuarios(token: String): List<Usuario> =
        api.getUsuarios("Bearer $token").body() ?: emptyList()
    suspend fun fetchUsuario(id: Long, token: String): Usuario? =
        api.getUsuario(id, "Bearer $token").body()


    //ANterior
    /*suspend fun obtenerUsuarios(): List<Usuario> {
        return RetrofitClient.apiService.obtenerUsuarios()
    }

    suspend fun obtenerUsuario(id: Long): Usuario {
        return RetrofitClient.apiService.obtenerUsuario(id)
    }

    suspend fun guardarUsuario(usuario: Usuario): Usuario {
        return RetrofitClient.apiService.guardarUsuario(usuario)
    }

    suspend fun eliminarUsuario(id: Long) {
        return RetrofitClient.apiService.eliminarUsuario(id)
    }*/
}
