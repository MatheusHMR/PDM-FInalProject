package pdm.compose.trabalhofinalpdm.ui.screens.customers

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import pdm.compose.trabalhofinalpdm.data.DataProvider
import pdm.compose.trabalhofinalpdm.model.Customer
import pdm.compose.trabalhofinalpdm.ui.components.CustomOutlinedTextField
import pdm.compose.trabalhofinalpdm.ui.components.TextTitle
import pdm.compose.trabalhofinalpdm.viewmodel.MainViewModel
import pdm.compose.trabalhofinalpdm.viewmodel.factory.MainViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCustomerScreen(customerId: String?, navController: NavController) {

    val viewModel: MainViewModel = viewModel(
        factory = MainViewModelFactory(DataProvider.customerRepository)
    )

    val customers by viewModel.customers.collectAsState()
    val customer = customers.find { it.customerId == customerId }

    var customerCpf by remember(customer) { mutableStateOf(customer?.cpf ?: "") }
    var customerName by remember(customer) { mutableStateOf(customer?.name ?: "") }
    var customerPhone by remember(customer) { mutableStateOf(customer?.phone ?: "") }
    var customerAddress by remember(customer) { mutableStateOf(customer?.address ?: "") }
    var customerInstagram by remember(customer) { mutableStateOf(customer?.instagram ?: "")}

    customer?.let {
        Scaffold {
            innerPadding ->

            Column(modifier = Modifier
                .padding(innerPadding)
                .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(
                        onClick = { navController.navigate("customers") },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back to Customer List"
                        )
                    }
                }

                TextTitle(text = "Edit Customer")


                /*PUT CUSTOMERCPF HERE*/
                CustomOutlinedTextField(
                    value = customerCpf,
                    onValueChange = { /*Disabled field*/},
                    label = "Customer Cpf",
                    modifier = Modifier.fillMaxWidth(),
                    enabled = false
//                    validation = viewModel::validateCustomerName // Assuming you have this in your ViewModel
                )
                Spacer(modifier = Modifier.height(8.dp))

                CustomOutlinedTextField(
                    value = customerName,
                    onValueChange = { customerName = it },
                    label = "Customer Name",
                    modifier = Modifier.fillMaxWidth(),
//                    validation = viewModel::validateCustomerName // Assuming you have this in your ViewModel
                )
                Spacer(modifier = Modifier.height(8.dp))


                CustomOutlinedTextField(
                    value = customerPhone,
                    onValueChange = { customerPhone = it },
                    label = "Customer Phone",
                    modifier = Modifier.fillMaxWidth(),
//                    validation = viewModel::validateCustomerPhone // Assuming you have this
                )
                Spacer(modifier = Modifier.height(8.dp))

                CustomOutlinedTextField(
                    value = customerAddress,
                    onValueChange = { customerAddress = it },
                    label = "Customer Address",
                    modifier = Modifier.fillMaxWidth(),
//                    validation = viewModel::validateCustomerAddress // Assuming you have this
                )
                Spacer(modifier = Modifier.height(8.dp))

                CustomOutlinedTextField(
                    value = customerInstagram,
                    onValueChange = { customerInstagram = it },
                    label = "Customer Instagram",
                    modifier = Modifier.fillMaxWidth(),
//                    validation = viewModel::validateCustomerAddress // Assuming you have this
                )
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        val updatedCustomer = customer.copy(
                            name = customerName,
                            address = customerAddress,
                            phone = customerPhone,
                            instagram = customerInstagram
                        )
                        viewModel.updateCustomer(updatedCustomer) // Assuming you have this in your ViewModel
                        navController.navigate("customers") // Adjust the navigation route as needed
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Changes")
                }
            }
        }
    } ?: run {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

}