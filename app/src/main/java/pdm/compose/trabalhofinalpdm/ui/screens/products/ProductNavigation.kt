package pdm.compose.trabalhofinalpdm.ui.screens.products

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductNavigation(
    navController: NavHostController, // Main NavController
    viewModelStoreOwner: ViewModelStoreOwner
) {
    CompositionLocalProvider(
        LocalViewModelStoreOwner provides viewModelStoreOwner
    ) {
        val nestedNavController = rememberNavController()
        NavHost(
            navController = nestedNavController,
            startDestination = "products"
        ) {
            composable("products") { ProductScreen(nestedNavController) }
            composable("products/addProduct") { AddProductScreen(nestedNavController) } // Assuming you have this screen
            composable("products/detailedProduct/{productId}") { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId") ?: ""
                DetailedProductScreen(productId, nestedNavController) // Assuming you have this screen
            }
            composable("products/editProduct/{productId}") { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId") ?: ""
                EditProductScreen(productId, nestedNavController) // Assuming you have this screen
            }
        }
    }
}