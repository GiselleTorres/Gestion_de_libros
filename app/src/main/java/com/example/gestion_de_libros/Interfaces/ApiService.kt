package com.example.gestion_de_libros.Interfaces

import retrofit2.http.GET
import retrofit2.http.*
import com.example.gestion_de_libros.Model.*
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.time.LocalDate

interface ApiService {

    ////////////////
    // Data class para enviar credenciales
    data class LoginRequest(val email: String, val password: String)
    // Data class para recibir respuesta de login
    data class LoginResponse(val token: String)
    data class RegisterRequest(val email: String, val password: String)
    data class AuthResponse(val token: String)


    @POST("/api/auth/login")
    suspend fun login(
        @Body credentials: LoginRequest
    ): Response<LoginResponse>
    @POST("/api/auth/register")

    suspend fun register(
        @Body registration: RegisterRequest
    ): Response<AuthResponse>
     ///////////////

    @PUT("/api/libros/{id}")
    suspend fun updateLibro(
        @Header("Authorization") token: String,
        @Path("id") id: Long,
        @Body libro: Libro
    ): Response<Libro>

    @DELETE("/api/libros/{id}")
    suspend fun deleteLibro(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<Unit>

    @POST("/api/libros")
    suspend fun createLibro(
        @Header("Authorization") token: String,
        @Body libro: Libro
    ): Response<Libro>

    @POST("/api/categorias")
    suspend fun createCategoria(
        @Header("Authorization") token: String,
        @Body categoria: Categoria
    ): Response<Categoria>

    @PUT("/api/usuarios/{id}")
    suspend fun updateUsuario(
        @Header("Authorization") token: String,
        @Path("id") id: Long,
        @Body usuario: Usuario
    ): Response<Usuario>

    @DELETE("/api/usuarios/{id}")
    suspend fun deleteUsuario(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<Unit>

    @POST("/api/usuarios")
    suspend fun createUsuario(
        @Header("Authorization") token: String,
        @Body usuario: Usuario
    ): Response<Usuario>

    @GET("/api/categorias")
    suspend fun getCategorias(@Header("Authorization") token: String): Response<List<Categoria>>

    @GET("/api/categorias/{id}")
    suspend fun getCategoria(@Path("id") id: Long, @Header("Authorization") token: String): Response<Categoria>

    @GET("/api/libros")
    suspend fun getLibros(@Header("Authorization") token: String): Response<List<Libro>>

    @GET("/api/libros/{id}")
    suspend fun getLibro(@Path("id") id: Long, @Header("Authorization") token: String): Response<Libro>

    @POST("/api/prestamos")
    suspend fun createPrestamo(
        @Header("Authorization") token: String,
        @Body prestamo: Prestamo
    ): Response<Prestamo>

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

