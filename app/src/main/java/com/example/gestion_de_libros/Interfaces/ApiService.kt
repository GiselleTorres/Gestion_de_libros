package com.example.gestion_de_libros.Interfaces

import retrofit2.http.GET
import retrofit2.http.*
import com.example.gestion_de_libros.Model.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface ApiService {
    // ---- LIBROS ----
    /*@GET("api/libros")
    suspend fun obtenerLibros(): List<Libro>

    @GET("api/libros/{id}")
    suspend fun obtenerLibro(@Path("id") id: Libro): Libro

    @POST("api/libros")
    suspend fun guardarLibro(@Body libro: Libro): Libro

    @DELETE("api/libros/{id}")
    suspend fun eliminarLibro(@Path("id") id: Long)

    // ---- CATEGORIAS ----
    @GET("api/categorias")
    suspend fun obtenerCategorias(): List<Categoria>

    @GET("api/categorias/{id}")
    suspend fun obtenerCategoria(@Path("id") id: Long): Categoria

    @POST("api/categorias")
    suspend fun guardarCategoria(@Body categoria: Categoria): Categoria

    @DELETE("api/categorias/{id}")
    suspend fun eliminarCategoria(@Path("id") id: Long)

    // ---- USUARIOS ----
    @GET("api/usuarios")
    suspend fun obtenerUsuarios(): List<Usuario>

    @GET("api/usuarios/{id}")
    suspend fun obtenerUsuario(@Path("id") id: Long): Usuario

    @POST("api/usuarios")
    suspend fun guardarUsuario(@Body usuario: Usuario): Usuario

    @DELETE("api/usuarios/{id}")
    suspend fun eliminarUsuario(@Path("id") id: Long)

    // ---- PRESTAMOS ----
    @GET("api/prestamos")
    suspend fun obtenerPrestamos(): List<Prestamo>

    @GET("api/prestamos/{id}")
    suspend fun obtenerPrestamo(@Path("id") id: Long): Prestamo

    @POST("api/prestamos")
    suspend fun guardarPrestamo(@Body prestamo: Prestamo): Prestamo

    @DELETE("api/prestamos/{id}")
    suspend fun eliminarPrestamo(@Path("id") id: Long) */

    ///////////////////
    @GET("/api/categorias")
    suspend fun getCategorias(@Header("Authorization") token: String): Response<List<Categoria>>

    @GET("/api/categorias/{id}")
    suspend fun getCategoria(@Path("id") id: Long, @Header("Authorization") token: String): Response<Categoria>

    @GET("/api/libros")
    suspend fun getLibros(@Header("Authorization") token: String): Response<List<Libro>>

    @GET("/api/libros/{id}")
    suspend fun getLibro(@Path("id") id: Long, @Header("Authorization") token: String): Response<Libro>

    @GET("/api/prestamos")
    suspend fun getPrestamos(@Header("Authorization") token: String): Response<List<Prestamo>>

    @GET("/api/prestamos/{id}")
    suspend fun getPrestamo(@Path("id") id: Long, @Header("Authorization") token: String): Response<Prestamo>

    @GET("/api/usuarios")
    suspend fun getUsuarios(@Header("Authorization") token: String): Response<List<Usuario>>

    @GET("/api/usuarios/{id}")
    suspend fun getUsuario(@Path("id") id: Long, @Header("Authorization") token: String): Response<Usuario>

}

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080" // para emulador Android

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
