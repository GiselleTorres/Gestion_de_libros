package com.example.gestion_de_libros.Model

data class Libro(
    val idLibro: Long? = null,
    val titulo: String,
    val autor: String,
    val añoPublicacion: Int,
    val isbn: String,
    val categoria: Categoria,
    val prestamos: List<Prestamo>
)



