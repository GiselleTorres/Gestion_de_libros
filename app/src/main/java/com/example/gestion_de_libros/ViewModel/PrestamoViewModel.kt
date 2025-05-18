package com.example.gestion_de_libros.ViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.gestion_de_libros.Model.Prestamo
import com.example.gestion_de_libros.Repository.PrestamoRepository
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PrestamoViewModel (private val repo: PrestamoRepository) : ViewModel() {

    private val _prestamos = MutableStateFlow<List<Prestamo>>(emptyList())
    val prestamos = _prestamos.asStateFlow()

    fun loadPrestamos(token: String) {
        viewModelScope.launch {
            _prestamos.value = repo.fetchPrestamos(token)
        }
    }
}


