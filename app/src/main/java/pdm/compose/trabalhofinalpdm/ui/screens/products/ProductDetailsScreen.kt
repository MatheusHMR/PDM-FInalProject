package pdm.compose.trabalhofinalpdm.ui.screens.products // Adjust package if needed

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import pdm.compose.trabalhofinalpdm.data.DataProvider
import pdm.compose.trabalhofinalpdm.model.toAttributeMap
import pdm.compose.trabalhofinalpdm.ui.components.TextLabel
import pdm.compose.trabalhofinalpdm.ui.components.TextTitle
import pdm.compose.trabalhofinalpdm.viewmodel.MainViewModel
import pdm.compose.trabalhofinalpdm.viewmodel.factory.MainViewModelFactory

@Composable
fun DetailedProductScreen(
    productId: String,
    navController: NavController
) {
    val viewModel: MainViewModel = viewModel(
        factory = MainViewModelFactory(productRepository = DataProvider.productRepository)
    )
    val products by viewModel.products.collectAsState()
    val product = products.find { it.productId == productId }
    Log.d("ProductDetailsScreen", "Product Id: $productId")

    Scaffold(
        floatingActionButton = {
            if (product != null) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate("products/editProduct/$productId") // Adjust navigation route
                        Log.d("ProductDetailsScreen","Product that's going to be edited: ${product.grainType}")
                    }
                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Product")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(
                    onClick = { navController.navigate("products") }, // Adjust navigation route
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to Product List"
                    )
                }
            }

            if (product != null) {
                val productAttributes = product.toAttributeMap()

                TextTitle(
                    text = "Product Details",
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                )

                Column(
                    modifier = Modifier.padding(4.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    productAttributes.forEach { (name, value) ->
                        Column(
                            modifier = Modifier.padding(4.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            TextLabel(text = name)
                            ProductAttributeCard(value = value)
                        }
                    }
                }
            } else {
                Text("Product not found")
            }
        }
    }
}

@Composable
fun ProductAttributeCard(value: String) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary,
            disabledContainerColor = MaterialTheme.colorScheme.tertiary,
            disabledContentColor = MaterialTheme.colorScheme.onTertiary
        )
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp
            )
        }
    }
}