package pdm.compose.trabalhofinalpdm.ui.screens.orders

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pdm.compose.trabalhofinalpdm.ui.screens.products.AddProductScreen
import pdm.compose.trabalhofinalpdm.ui.screens.products.DetailedProductScreen
import pdm.compose.trabalhofinalpdm.ui.screens.products.EditProductScreen
import pdm.compose.trabalhofinalpdm.ui.screens.products.ProductScreen

@Composable
fun OrderNavigation(
    navController: NavHostController, // Main NavController
    viewModelStoreOwner: ViewModelStoreOwner
) {
    CompositionLocalProvider(
        LocalViewModelStoreOwner provides viewModelStoreOwner
    ) {
        val nestedNavController = rememberNavController()
        NavHost(
            navController = nestedNavController,
            startDestination = "orders"
        ) {
            composable("orders") { OrderScreen(nestedNavController) }
            composable("orders/addOrder") { AddOrderScreen(nestedNavController) } // Assuming you have this screen
//            composable("orders/detailedOrder/{orderId}") { backStackEntry ->
//                val orderId = backStackEntry.arguments?.getString("orderId") ?: ""
//                OrderDetailsScreen(orderId, nestedNavController) // Assuming you have this screen
//            composable("orders/editOrder/{productId}") { backStackEntry ->
//                val orderId = backStackEntry.arguments?.getString("orderId") ?: ""
//                EditOrderScreen(orderId, nestedNavController) // Assuming you have this screen
//            }
        }
    }
}