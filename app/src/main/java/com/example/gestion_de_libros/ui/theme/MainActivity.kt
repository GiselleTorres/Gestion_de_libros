package com.example.gestion_de_libros.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gestion_de_libros.Interfaces.ApiService
import com.example.gestion_de_libros.Repository.CategoriaRepository
import com.example.gestion_de_libros.Repository.LibroRepository
import com.example.gestion_de_libros.Repository.LoginRepository
import com.example.gestion_de_libros.Repository.PrestamoRepository
import com.example.gestion_de_libros.Repository.UsuarioRepository
import com.example.gestion_de_libros.ViewModel.CategoriaViewModel
import com.example.gestion_de_libros.ViewModel.LibroViewModel
import com.example.gestion_de_libros.ViewModel.PrestamoViewModel
import com.example.gestion_de_libros.ViewModel.UsuarioViewModel
import com.example.gestion_de_libros.Screen.CategoriaScreen
import com.example.gestion_de_libros.Screen.LibroScreen
import com.example.gestion_de_libros.Screen.PrestamoScreen
import com.example.gestion_de_libros.Screen.UsuarioScreen
import com.example.gestion_de_libros.Screen.InicioScreen
import com.example.gestion_de_libros.Screen.LoginScreen
import com.example.gestion_de_libros.ViewModel.LoginViewModel
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configura Retrofit con Gson lenient
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val api = retrofit.create(ApiService::class.java)

        // Repositorios y ViewModels
        val usuarioRepo = UsuarioRepository(api)
        val authRepo = LoginRepository(usuarioRepo)
        val authVm = LoginViewModel(authRepo)
        val catVm = CategoriaViewModel(CategoriaRepository(api))
        val libVm = LibroViewModel(LibroRepository(api))
        val presVm = PrestamoViewModel(PrestamoRepository(api))
        val usrVm = UsuarioViewModel(UsuarioRepository(api))

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "login") {
                        // Login
                        composable("login") {
                            LoginScreen(navController, authVm)
                        }
                        // Inicio tras login
                        composable(
                            route = "inicio/{token}",
                            arguments = listOf(navArgument("token") { type = NavType.StringType })
                        ) { backStack ->
                            val token = backStack.arguments?.getString("token").orEmpty()
                            InicioScreen(navController, token)
                        }
                        // Categorías
                        composable(
                            route = "categorias/{token}",
                            arguments = listOf(navArgument("token") { type = NavType.StringType })
                        ) { backStack ->
                            val token = backStack.arguments?.getString("token").orEmpty()
                            CategoriaScreen(navController, catVm, token)
                        }
                        // Libros
                        composable(
                            route = "libros/{token}",
                            arguments = listOf(navArgument("token") { type = NavType.StringType })
                        ) { backStack ->
                            val token = backStack.arguments?.getString("token").orEmpty()
                            LibroScreen(navController, libVm, catVm, token)
                        }
                        // Préstamos
                        composable(
                            route = "prestamos/{token}",
                            arguments = listOf(navArgument("token") { type = NavType.StringType })
                        ) { backStack ->
                            val token = backStack.arguments?.getString("token").orEmpty()
                            PrestamoScreen(navController, presVm, usrVm, libVm, token)
                        }
                        // Usuarios
                        composable(
                            route = "usuarios/{token}",
                            arguments = listOf(navArgument("token") { type = NavType.StringType })
                        ) { backStack ->
                            val token = backStack.arguments?.getString("token").orEmpty()
                            UsuarioScreen(navController, usrVm, token)
                        }
                    }
                }
            }
        }
    }
}