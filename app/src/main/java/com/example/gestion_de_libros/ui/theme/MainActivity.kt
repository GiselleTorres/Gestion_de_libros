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
import com.example.gestion_de_libros.Screen.InicioScreen
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

        // ViewModels
        val catVm = CategoriaViewModel(CategoriaRepository(api))
        val libVm = LibroViewModel(LibroRepository(api))
        val presVm = PrestamoViewModel(PrestamoRepository(api))
        val usrVm = UsuarioViewModel(UsuarioRepository(api))

        // Token de ejemplo
        val token = "TU_TOKEN_AQUI"

        setContent {
            MaterialTheme {
                Surface {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "start") {
                        composable("start") {
                            LaunchedEffect(Unit) {
                                navController.navigate("inicio/$token") {
                                    popUpTo("start") { inclusive = true }
                                }
                            }
                        }
                        composable(
                            "inicio/{token}",
                            arguments = listOf(navArgument("token") { type = NavType.StringType })
                        ) { backStack ->
                            val argToken = backStack.arguments?.getString("token").orEmpty()
                            InicioScreen(navController, argToken)
                        }
                        composable(
                            "categorias/{token}",
                            arguments = listOf(navArgument("token") { type = NavType.StringType })
                        ) { backStack ->
                            val argToken = backStack.arguments?.getString("token").orEmpty()
                            CategoriaScreen(navController, catVm, argToken)
                        }
                        composable(
                            "libros/{token}",
                            arguments = listOf(navArgument("token") { type = NavType.StringType })
                        ) { backStack ->
                            val argToken = backStack.arguments?.getString("token").orEmpty()
                            LibroScreen(navController, libVm, catVm, argToken)
                        }
                        composable(
                            "prestamos/{token}",
                            arguments = listOf(navArgument("token") { type = NavType.StringType })
                        ) { backStack ->
                            val argToken = backStack.arguments?.getString("token").orEmpty()
                            PrestamoScreen(navController, presVm, argToken)
                        }
                        composable(
                            "usuarios/{token}",
                            arguments = listOf(navArgument("token") { type = NavType.StringType })
                        ) { backStack ->
                            val argToken = backStack.arguments?.getString("token").orEmpty()
                            UsuarioScreen(navController, usrVm, argToken)
                        }
                    }
                }
            }
        }
    }
}