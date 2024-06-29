package pdm.compose.trabalhofinalpdm.ui.screens.customers

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
fun CustomerNavigation(
    navController: NavHostController, //Main NavController
    viewModelStoreOwner: ViewModelStoreOwner
) { 
    CompositionLocalProvider(
        LocalViewModelStoreOwner provides viewModelStoreOwner
    ) {
        val nestedNavController = rememberNavController()
        NavHost(
            navController = nestedNavController,
            startDestination = "customers",
        ) {
            composable("customers") { CustomerScreen(nestedNavController) }
            composable("customers/addCustomer") { AddCustomerScreen(nestedNavController) }
            composable("customers/detailedCustomer/{customerId}") { backStackEntry ->
                val customerId = backStackEntry.arguments?.getString("customerId") ?: ""
                DetailedCustomerScreen(customerId, nestedNavController)
            }
            composable("customers/editCustomer/{customerId}") { backStackEntry ->
                val customerId = backStackEntry.arguments?.getString("customerId") ?: ""
                EditCustomerScreen(customerId, nestedNavController)
            }
        }
    }

}