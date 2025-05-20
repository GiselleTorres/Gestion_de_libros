package com.example.gestion_de_libros.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestion_de_libros.Model.Categoria
import com.example.gestion_de_libros.Repository.CategoriaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoriaViewModel (private val repo: CategoriaRepository): ViewModel () {
    private val _categorias = MutableStateFlow<List<Categoria>>(emptyList())
    val categorias = _categorias.asStateFlow()

    fun loadCategorias(token: String) {
        viewModelScope.launch {
            _categorias.value = repo.fetchCategorias(token)
        }
    }

    //método para agregar una categoría
    fun addCategoria(token: String, categoria: Categoria, onResult: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            val created = repo.createCategoria(token, categoria)
            onResult(created != null)
            // Recarga la lista tras crear
            loadCategorias(token)
        }
    }
}
