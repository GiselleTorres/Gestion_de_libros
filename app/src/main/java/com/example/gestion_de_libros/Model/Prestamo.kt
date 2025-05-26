package com.example.gestion_de_libros.Model

import java.time.LocalDate;

data class Prestamo(
    val idPrestamo: Long?= null,
    val fechaInicio: String,
    val fechaFin: String,
    val usuario: Usuario,
    val libro: Libro
)