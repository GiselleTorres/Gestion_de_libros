package com.example.gestion_de_libros.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gestion_de_libros.Interfaces.ApiService
import com.example.gestion_de_libros.Repository.CategoriaRepository
import com.example.gestion_de_libros.Repository.LibroRepository
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
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.gestion_de_libros.Screen.InicioScreen


@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar Retrofit con Gson lenient
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val apiService = retrofit.create(ApiService::class.java)

        // Instanciar ViewModels con sus repositorios
        val catVm = CategoriaViewModel(CategoriaRepository(apiService))
        val libVm = LibroViewModel(LibroRepository(apiService))
        val presVm = PrestamoViewModel(PrestamoRepository(apiService))
        val usrVm = UsuarioViewModel(UsuarioRepository(apiService))

        // Token de ejemplo (debe obtenerse tras login real)
        val token = "TU_TOKEN_AQUI"

        setContent {
            MaterialTheme {
                Surface {
                    val navController = rememberNavController()

                    // Navegación principal
                    NavHost(
                        navController = navController,
                        startDestination = "start"
                    ) {
                        // Pantalla invisible al iniciar: redirige a menú de inicio
                        composable("start") {
                            LaunchedEffect(Unit) {
                                navController.navigate("inicio/$token") {
                                    popUpTo("start") { inclusive = true }
                                }
                            }
                        }
                        // Menú de inicio con botones
                        composable(
                            route = "inicio/{token}",
                            arguments = listOf(navArgument("token") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val argToken = backStackEntry.arguments?.getString("token").orEmpty()
                            InicioScreen(navController, argToken)
                        }
                        // Rutas que reciben el token como argumento
                        composable(
                            route = "categorias/{token}",
                            arguments = listOf(navArgument("token") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val argToken = backStackEntry.arguments?.getString("token").orEmpty()
                            CategoriaScreen(navController, catVm, argToken)
                        }
                        composable(
                            route = "libros/{token}",
                            arguments = listOf(navArgument("token") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val argToken = backStackEntry.arguments?.getString("token").orEmpty()
                            LibroScreen(navController, libVm, argToken)
                        }
                        composable(
                            route = "prestamos/{token}",
                            arguments = listOf(navArgument("token") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val argToken = backStackEntry.arguments?.getString("token").orEmpty()
                            PrestamoScreen(navController, presVm, argToken)
                        }
                        composable(
                            route = "usuarios/{token}",
                            arguments = listOf(navArgument("token") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val argToken = backStackEntry.arguments?.getString("token").orEmpty()
                            UsuarioScreen(navController, usrVm, argToken)
                        }
                    }
                }
            }
        }
    }
}



