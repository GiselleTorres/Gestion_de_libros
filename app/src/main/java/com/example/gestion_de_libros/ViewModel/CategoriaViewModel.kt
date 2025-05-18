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

//Anterior
    /*private val repository= CategoriaRepository()

    // Lista de categorías
    private val _categorias = MutableLiveData<List<Categoria>?>(emptyList())
    val categorias: MutableLiveData<List<Categoria>?> = _categorias

    // Detalle de una categoría
    private val _categoria = MutableLiveData<Categoria?>()
    val categoria: LiveData<Categoria?> = _categoria

    init {
        obtenerCategorias()
    }

    /** Carga todas las categorías desde el repositorio */
    fun obtenerCategorias() {
        viewModelScope.launch {
            val categoriasList = withContext(Dispatchers.IO) {
                repository.obtenerCategorias()
            }
            _categorias.postValue(categoriasList)
        }
    }

    /** Carga una categoría por su ID */
    fun obtenerCategoria(id: Long) {
        viewModelScope.launch {
            val resultado = withContext(Dispatchers.IO) {
                repository.obtenerCategoria(id)
            }
            _categoria.postValue(resultado)
        }
    }

    /** Crea una nueva categoría y refresca la lista */
    fun crearCategoria(nueva: Categoria) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.guardarCategoria(nueva)
            }
            obtenerCategorias()
        }
    }

    /** Elimina una categoría y refresca la lista */
    fun eliminarCategoria(id: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.eliminarCategoria(id)
            }
            obtenerCategorias()
        }
    }*/
}
