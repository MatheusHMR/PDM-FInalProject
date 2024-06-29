package pdm.compose.trabalhofinalpdm.ui.screens.products // Adjust package if needed

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import pdm.compose.trabalhofinalpdm.data.DataProvider
import pdm.compose.trabalhofinalpdm.model.enums.GrainType
import pdm.compose.trabalhofinalpdm.model.enums.RoastingPoint
import pdm.compose.trabalhofinalpdm.ui.components.CustomOutlinedTextField
import pdm.compose.trabalhofinalpdm.ui.components.DropdownMenuComponent
import pdm.compose.trabalhofinalpdm.ui.components.TextTitle
import pdm.compose.trabalhofinalpdm.viewmodel.MainViewModel
import pdm.compose.trabalhofinalpdm.viewmodel.factory.MainViewModelFactory

@Composable
fun EditProductScreen(productId: String?, navController: NavController) {
    val viewModel: MainViewModel = viewModel(
        factory = MainViewModelFactory(productRepository = DataProvider.productRepository)
    )

    val products by viewModel.products.collectAsState()
    val product = products.find { it.productId == productId }

    var selectedGrainType by remember(product) { mutableStateOf(product?.grainType ?: GrainType.ARABICA_CERRADO) }
    var selectedRoastingPoint by remember(product) {mutableStateOf(product?.roastingPoint ?: RoastingPoint.MEDIUM) }
    var productValue by remember(product) { mutableStateOf(product?.price?.toString() ?: "") }
    var isBlend by remember(product) { mutableStateOf(product?.isBlend ?: false) }
    var imageUrl by remember(product) { mutableStateOf(product?.imageUrl ?: "") }

    product?.let {
        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(
                        onClick = { navController.navigate("products") }, // Adjust navigation route
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back to Product List"
                        )
                    }
                }

                TextTitle(text = "Edit Product")

                // Grain Type Dropdown
                DropdownMenuComponent(
                    label = "Grain Type",
                    selectedOption = selectedGrainType.name,
                    options = GrainType.entries.map { it.name },
                    onSelectionChange = { selectedGrainType = GrainType.valueOf(it) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Roasting Point Dropdown
                DropdownMenuComponent(
                    label = "Roasting Point",
                    selectedOption = selectedRoastingPoint.name,
                    options = RoastingPoint.entries.map { it.name },
                    onSelectionChange = { selectedRoastingPoint = RoastingPoint.valueOf(it) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                CustomOutlinedTextField(
                    value = productValue,
                    onValueChange = { productValue = it },
                    label = "Price",
                    modifier = Modifier.fillMaxWidth(),
                    keyboardType = KeyboardType.Number
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Is Blend Checkbox
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = isBlend,
                        onCheckedChange = { isBlend = it }
                    )
                    Text("Is Blend")
                }

                Spacer(modifier = Modifier.height(8.dp))

                CustomOutlinedTextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = "Image URL",
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        val updatedProduct = product.copy(
                            grainType = selectedGrainType,
                            roastingPoint = selectedRoastingPoint,
                            price = productValue.toDoubleOrNull() ?: 0.0,
                            isBlend = isBlend,
                            imageUrl = imageUrl
                        )
                        viewModel.updateProduct(updatedProduct) // Implement updateProduct in ViewModel
                        navController.navigate("products") // Adjust navigation route
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Changes")
                }
            }
        }
    } ?: run {
        // Handle case where product is not found (e.g., show a loading indicator)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}