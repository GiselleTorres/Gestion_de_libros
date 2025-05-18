package com.example.gestion_de_libros.Model

data class Usuario(

    val idUsuario: Long? = null,
    val nombre: String,
    val email: String,
    val telefono: String,
    val prestamos: List<Prestamo>
)


