package com.example.gestion_de_libros.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestion_de_libros.Repository.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para manejo de login usando repositorio local de usuarios.
 */
class LoginViewModel(private val repo: LoginRepository) : ViewModel() {
    private val _token = MutableStateFlow<String?>(null)
    val token = _token.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    /**
     * Si el email coincide y la contraseña == nombre, asigna email como token.
     */
    fun login(email: String, password: String) {
        viewModelScope.launch {
            val valid = repo.login(email, password)
            if (valid) {
                _token.value = email
            } else {
                _error.value = "Credenciales inválidas"
            }
        }
    }

    /**
     * Registro no disponible
     */
    fun register(email: String, password: String) {
        _error.value = "Registro no disponible"
    }
}