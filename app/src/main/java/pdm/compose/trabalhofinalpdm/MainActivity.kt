package pdm.compose.trabalhofinalpdm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.invalidateGroupsWithKey
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pdm.compose.trabalhofinalpdm.ui.screens.CoffeeTopAppBar
import pdm.compose.trabalhofinalpdm.ui.theme.TrabalhoFinalPDMTheme
import pdm.compose.trabalhofinalpdm.ui.screens.Navbar
import pdm.compose.trabalhofinalpdm.ui.screens.customers.AddCustomerScreen
import pdm.compose.trabalhofinalpdm.ui.screens.customers.CustomerNavigation
import pdm.compose.trabalhofinalpdm.ui.screens.home.HomeScreen
import pdm.compose.trabalhofinalpdm.ui.screens.orders.OrderNavigation
import pdm.compose.trabalhofinalpdm.ui.screens.orders.OrderScreen
import pdm.compose.trabalhofinalpdm.ui.screens.products.ProductNavigation
import pdm.compose.trabalhofinalpdm.ui.screens.products.ProductScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrabalhoFinalPDMTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ){
                    val navController = rememberNavController()
                    Navigation(navController, this)
                    // Pass 'this' (MainActivity) as ViewModelStoreOwner
                    //Who provides our ViewModel so that we can use it among the different navigations!
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(
    navController: NavHostController,
    viewModelStoreOwner: ViewModelStoreOwner
    ) {
    Scaffold(
        topBar = { CoffeeTopAppBar(navController = navController) },
        bottomBar = { Navbar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen(navController) }
            composable("customers") { CustomerNavigation(navController, viewModelStoreOwner) }
            composable("products") { ProductNavigation(navController, viewModelStoreOwner) }
            composable("orders") { OrderNavigation(navController, viewModelStoreOwner) }
        }
    }
}