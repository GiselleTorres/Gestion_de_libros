package com.example.gestion_de_libros.ViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.gestion_de_libros.Model.Usuario
import com.example.gestion_de_libros.Repository.UsuarioRepository
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UsuarioViewModel (private val repo: UsuarioRepository) : ViewModel(){

    private val _usuarios = MutableStateFlow<List<Usuario>>(emptyList())
    val usuarios = _usuarios.asStateFlow()

    fun loadUsuarios(token: String) {
        viewModelScope.launch {
            _usuarios.value = repo.fetchUsuarios(token)
        }
    }
    // método para agregar un usuario
    fun addUsuario(token: String, usuario: Usuario, onResult: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            val created = repo.createUsuario(token, usuario)
            onResult(created != null)
            // Refrescar lista tras creación
            loadUsuarios(token)
        }
    }
}