package pdm.compose.trabalhofinalpdm.ui.screens.customers

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import pdm.compose.trabalhofinalpdm.data.DataProvider
import pdm.compose.trabalhofinalpdm.model.Customer
import pdm.compose.trabalhofinalpdm.ui.components.CustomOutlinedTextField
import pdm.compose.trabalhofinalpdm.ui.components.TextTitle
import pdm.compose.trabalhofinalpdm.ui.components.ThreadLaunchingButton
import pdm.compose.trabalhofinalpdm.viewmodel.CustomerViewModel
import pdm.compose.trabalhofinalpdm.viewmodel.MainViewModel
import pdm.compose.trabalhofinalpdm.viewmodel.factory.CustomerViewModelFactory
import pdm.compose.trabalhofinalpdm.viewmodel.factory.MainViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCustomerScreen(navController: NavController) {

    val viewModel: CustomerViewModel = viewModel(
        factory = CustomerViewModelFactory(DataProvider.customerRepository)
    )

    var customerCpf by remember { mutableStateOf("") }
    var customerName by remember { mutableStateOf("") }
    var customerPhone by remember { mutableStateOf("") }
    var customerAddress by remember { mutableStateOf("") }
    var customerInstagram by remember { mutableStateOf("") }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 0.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = {
//                    navController.popBackStack()
                    navController.navigate("customers")
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to Customer List",
                    )
                }
            }

            TextTitle(text = "Add New Customer")

            CustomOutlinedTextField(
                value = customerCpf,
                onValueChange = { customerCpf = it },
                label = "Customer CPF",
                modifier = Modifier.fillMaxWidth(),
//                validation = viewModel::validateCustomerName
            )

            Spacer(modifier = Modifier.height(4.dp))

            CustomOutlinedTextField(
                value = customerName,
                onValueChange = { customerName = it },
                label = "Customer Name",
                modifier = Modifier.fillMaxWidth(),
//                validation = viewModel::validateCustomerName
            )

            Spacer(modifier = Modifier.height(4.dp))

            CustomOutlinedTextField(
                value = customerPhone,
                onValueChange = { customerPhone = it },
                label = "Phone Number",
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Phone,
//                validation = viewModel::validateCustomerPhone
            )

            Spacer(modifier = Modifier.height(4.dp))

            CustomOutlinedTextField(
                value = customerAddress,
                onValueChange = { customerAddress = it },
                label = "Address",
                modifier = Modifier.fillMaxWidth(),
//                validation = viewModel::validateCustomerAddress
            )

            Spacer(modifier = Modifier.height(4.dp))

            CustomOutlinedTextField(
                value = customerInstagram,
                onValueChange = { customerInstagram = it },
                label = "Instagram",
                modifier = Modifier.fillMaxWidth(),
//                validation = viewModel::validateCustomerCity
            )

            Spacer(modifier = Modifier.height(8.dp))

            ThreadLaunchingButton(
                onClick = {
                    val newCustomer = Customer(
                        cpf = customerCpf,
                        name = customerName,
                        phone = customerPhone,
                        address = customerAddress,
                        instagram = customerInstagram
                    )
                    viewModel.addCustomer(newCustomer)
                    Log.i("AddCustomerScreen", "Added customer: ${newCustomer.name}")
//                    navController.popBackStack()
                    navController.navigate("customers") // Navigate back after adding
                },
                text = "Add Customer",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}