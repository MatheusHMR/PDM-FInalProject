package pdm.compose.trabalhofinalpdm.ui.screens.products

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import pdm.compose.trabalhofinalpdm.data.DataProvider
import pdm.compose.trabalhofinalpdm.model.Product
import pdm.compose.trabalhofinalpdm.model.enums.GrainType
import pdm.compose.trabalhofinalpdm.model.enums.RoastingPoint
import pdm.compose.trabalhofinalpdm.ui.components.CustomOutlinedTextField
import pdm.compose.trabalhofinalpdm.ui.components.DropdownMenuComponent
import pdm.compose.trabalhofinalpdm.ui.components.TextTitle
import pdm.compose.trabalhofinalpdm.ui.components.ThreadLaunchingButton
import pdm.compose.trabalhofinalpdm.viewmodel.MainViewModel
import pdm.compose.trabalhofinalpdm.viewmodel.factory.MainViewModelFactory


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(navController: NavController) {
    val viewModel: MainViewModel = viewModel(
        factory = MainViewModelFactory(productRepository = DataProvider.productRepository)
    )

    var selectedGrainType by remember { mutableStateOf(GrainType.ARABICA_CERRADO)}
    var selectedRoastingPoint by remember { mutableStateOf(RoastingPoint.MEDIUM) }
    var productPrice by remember { mutableStateOf("") }
    var isBlend by remember { mutableStateOf(false) }
    var imageUrl by remember { mutableStateOf("") }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 0.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = { navController.navigate("products") }) { // Adjust navigation route
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to Product List"
                    )
                }
            }

            TextTitle(text = "Add New Product")

            // Grain Type Dropdown
            DropdownMenuComponent(
                label = "Grain Type",
                selectedOption = selectedGrainType.name,
                options = GrainType.entries.map { it.name },
                onSelectionChange = { selectedGrainType = GrainType.valueOf(it) }
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Roasting Point Dropdown
            DropdownMenuComponent(
                label = "Roasting Point",
                selectedOption = selectedRoastingPoint.name,
                options = RoastingPoint.entries.map { it.name },
                onSelectionChange = { selectedRoastingPoint = RoastingPoint.valueOf(it) }
            )

            Spacer(modifier = Modifier.height(4.dp))

            CustomOutlinedTextField(
                value = productPrice,
                onValueChange = { productPrice = it },
                label = "Price",
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Number
            )

            Spacer(modifier = Modifier.height(4.dp))

            CustomOutlinedTextField(
                value = imageUrl,
                onValueChange = { imageUrl = it },
                label = "Image URL",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            ThreadLaunchingButton(
                onClick = {
                    val newProduct = Product(
                        grainType = selectedGrainType,
                        roastingPoint = selectedRoastingPoint,
                        price = productPrice.toDoubleOrNull() ?: 0.0, // Handle potential parsing errors
                        isBlend = isBlend,
                        imageUrl = imageUrl
                    )
                    viewModel.addProduct(newProduct) // Implement addProduct in ViewModel
                    Log.i("AddProductScreen", "Added product: ${newProduct.grainType}")
                    navController.navigate("products") // Navigate back after adding
                },
                text = "Add Product",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}