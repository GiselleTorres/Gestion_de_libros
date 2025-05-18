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

    //Anterior

   /* private val repository= LibroRepository()

    private val _libros = MutableLiveData<List<Libro>?>(emptyList())
    val libros: MutableLiveData<List<Libro>?> = _libros

    fun obtenerLibros() {
        viewModelScope.launch {
            val librosList = withContext(Dispatchers.IO) {
                repository.obtenerLibros()
            }
            _libros.postValue(librosList)
        }
    }

    fun obtenerLibro(id: Libro) {
        viewModelScope.launch {
            val libro = withContext(Dispatchers.IO) {
                repository.obtenerLibro(id)
            }
        }
    }

    fun guardarLibro(libro: Libro){
        viewModelScope.launch {
            repository.guardarLibro(libro)
            obtenerLibros()
        }
    }

    fun eliminarLibro(id: Long) {
        viewModelScope.launch {
            repository.eliminarLibro(id)
            obtenerLibros()
        }
    }*/
}