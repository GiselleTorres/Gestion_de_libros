package com.example.gestion_de_libros.Model

import java.time.LocalDate;

data class Prestamo(
    val idPrestamo: Long?= null,
    val fechaInicio: LocalDate,
    val fechaFin: LocalDate,
    val usuario: List<Usuario>,
    val libro: List<Libro>
)