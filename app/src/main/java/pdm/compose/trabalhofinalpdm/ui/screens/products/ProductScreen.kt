package pdm.compose.trabalhofinalpdm.ui.screens.products

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import pdm.compose.trabalhofinalpdm.data.DataProvider
import pdm.compose.trabalhofinalpdm.model.Product
import pdm.compose.trabalhofinalpdm.ui.components.ConfirmationDialog
import pdm.compose.trabalhofinalpdm.ui.components.GenericActionMenu
import pdm.compose.trabalhofinalpdm.ui.components.GenericListItem
import pdm.compose.trabalhofinalpdm.ui.components.TextTitle
import pdm.compose.trabalhofinalpdm.viewmodel.MainViewModel
import pdm.compose.trabalhofinalpdm.viewmodel.ProductViewModel
import pdm.compose.trabalhofinalpdm.viewmodel.factory.MainViewModelFactory
import pdm.compose.trabalhofinalpdm.viewmodel.factory.ProductViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(navController: NavController) {

    val viewModel: ProductViewModel = viewModel(
        factory = ProductViewModelFactory(
            productRepository = DataProvider.productRepository
        )
    )
    val products by viewModel.products.collectAsState()
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("products/addProduct") }) { // Adjust navigation route
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Product")
            }}
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            TextTitle("Products")
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(products) { product ->
                    var expanded by remember { mutableStateOf(false) }

                    GenericListItem(
                        item = product,
                        displayContent = {
                            Column {
                                Text(
                                    text = "Product: ${product.name}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = "Grain Type: ${product.grainType.name}",
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = "Price: $${product.price}",
                                    fontSize = 16.sp
                                )
                            }
                        },
                        onCardClick = { navController.navigate("products/detailedProduct/${product.productId}") }, // Adjust navigation route
                        onMoreInfoClick = { navController.navigate("products/detailedProduct/${product.productId}") }, // Adjust navigation route
                        onEditClick = {
                            navController.navigate("products/editProduct/${product.productId}") // Adjust navigation route
                            expanded = false
                        },
                        onDeleteClick = {
                            selectedProduct = product
                            showDeleteConfirmation = true
                            expanded = false
                        },
                        expanded = expanded,
                        onExpandChange = { expanded = it }
                    )
                }
            }
        }
    }

    // Confirmation Dialog
    ConfirmationDialog(
        showDialog = showDeleteConfirmation,
        title = "Confirm Deletion",
        message = "Are you sure you want to delete <<${selectedProduct?.productId?.uppercase()}>>?", // Adjust message
        onConfirm = {
            selectedProduct?.let {
                viewModel.deleteProduct(it.productId) // Implement deleteProduct in ViewModel
                navController.popBackStack()
            }
            selectedProduct = null
            showDeleteConfirmation = false
        },
        onDismiss = {
            selectedProduct = null
            showDeleteConfirmation = false
        }
    )
}
