package pdm.compose.trabalhofinalpdm.ui.screens

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Data class to represent a navigation item
data class NavigationItem(val route: String, val icon: ImageVector, val label: String)

// List of navigation items
val navigationItems = listOf(
    NavigationItem("customers", Icons.Filled.AccountCircle, "Customers"),
    NavigationItem("products", Icons.Filled.Favorite, "Products"),
    NavigationItem("orders", Icons.Filled.ShoppingCart, "Orders")
)

@Composable
fun Navbar(navController: NavController = rememberNavController()) {
    NavigationBar(
        Modifier.height(64.dp)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        navigationItems.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon,
                    contentDescription = item.label,
                ) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTextColor = MaterialTheme.colorScheme.onSecondary,
                    indicatorColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}