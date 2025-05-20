package com.example.gestion_de_libros.ViewModel

import kotlinx.coroutines.launch
import com.example.gestion_de_libros.Model.Libro
import com.example.gestion_de_libros.Repository.LibroRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LibroViewModel (private val repo: LibroRepository) : ViewModel() {

    private val _libros = MutableStateFlow<List<Libro>>(emptyList())

    val libros = _libros.asStateFlow()

    fun loadLibros(token: String) {
            viewModelScope.launch {
                _libros.value = repo.fetchLibros(token)
            }
    }

    // Método para agregar un libro
    fun addLibro(token: String, libro: Libro, onResult: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            val created = repo.createLibro(token, libro)
            onResult(created != null)
            loadLibros(token)
        }
    }

    // Método para actualizar un libro
    fun updateLibro(token: String, libro: Libro, onResult: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            val updated = repo.updateLibro(token, libro)
            onResult(updated != null)
            loadLibros(token)
        }
    }

    // Método para eliminar un libro
    fun deleteLibro(token: String, id: Long, onResult: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            val success = repo.deleteLibro(token, id)
            onResult(success)
            loadLibros(token)
        }
    }
}