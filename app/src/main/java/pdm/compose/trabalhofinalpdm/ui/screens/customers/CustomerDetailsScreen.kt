package pdm.compose.trabalhofinalpdm.ui.screens.customers


import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import pdm.compose.trabalhofinalpdm.ui.components.TextLabel
import pdm.compose.trabalhofinalpdm.ui.components.TextTitle
import pdm.compose.trabalhofinalpdm.data.DataProvider
import pdm.compose.trabalhofinalpdm.model.toAttributeMap
import pdm.compose.trabalhofinalpdm.ui.components.GenericAttributeCard
import pdm.compose.trabalhofinalpdm.viewmodel.CustomerViewModel
import pdm.compose.trabalhofinalpdm.viewmodel.factory.CustomerViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailedCustomerScreen(
    customerId: String,
    navController: NavController
) {
    val viewModel: CustomerViewModel = viewModel(
        factory = CustomerViewModelFactory(DataProvider.customerRepository)
    )
    val customers by viewModel.customers.collectAsState()
    val customer = customers.find { it.customerId == customerId }
    Log.d("CustomerDetailsScreen", "Customer Id: $customerId")

    Scaffold(
        floatingActionButton = {
            if (customer != null) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate("customers/editCustomer/$customerId")
                        Log.d("CustomerDetailsScreen", "Customer that's going to be edited: ${customer.name}")
                    }
                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Customer")
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
                    onClick = { navController.navigate("customers") },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to Customer List"
                    )
                }
            }

            if (customer != null) {
                val customerAttributes = customer.toAttributeMap()

                TextTitle(text = "Customer Details",
                    modifier = Modifier.fillMaxWidth().padding(16.dp))

                Column(
                    modifier = Modifier.padding(4.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    customerAttributes.forEach { (name, value) ->
                        Column(
                            modifier = Modifier.padding(4.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            TextLabel(text = name)
                            GenericAttributeCard(value = value)
                        }
                    }
                }
            } else {
                Text("Customer not found")
            }
        }
    }
}

