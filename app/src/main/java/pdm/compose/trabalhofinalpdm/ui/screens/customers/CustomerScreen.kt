package pdm.compose.trabalhofinalpdm.ui.screens.customers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pdm.compose.trabalhofinalpdm.ui.components.ConfirmationDialog
import pdm.compose.trabalhofinalpdm.ui.components.GenericActionMenu
import pdm.compose.trabalhofinalpdm.model.Customer
import pdm.compose.trabalhofinalpdm.ui.components.TextTitle
import pdm.compose.trabalhofinalpdm.viewmodel.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import pdm.compose.trabalhofinalpdm.data.DataProvider
import pdm.compose.trabalhofinalpdm.ui.components.GenericListItem
import pdm.compose.trabalhofinalpdm.viewmodel.CustomerViewModel
import pdm.compose.trabalhofinalpdm.viewmodel.factory.CustomerViewModelFactory
import pdm.compose.trabalhofinalpdm.viewmodel.factory.MainViewModelFactory

@ExperimentalMaterial3Api
@Composable
fun CustomerScreen(navController: NavController) {

    val viewModel: CustomerViewModel = viewModel(
        factory = CustomerViewModelFactory(DataProvider.customerRepository)
    )
    val customers by viewModel.customers.collectAsState()
    var selectedCustomer by remember { mutableStateOf<Customer?>(null) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("customers/addCustomer") }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Customer")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            TextTitle("Customers")
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(customers) { customer ->
                    var expanded by remember { mutableStateOf(false) }

                    GenericListItem(
                        item = customer,
                        displayContent = {
                            Column {
                                Text(
                                    text = "Customer: ${customer.name}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = "Email: ${customer.address}",
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = "Phone: ${customer.phone}",
                                    fontSize = 16.sp)
                            }
                        },
                        onCardClick = { navController.navigate("customers/detailedCustomer/${customer.customerId}") },
                        onMoreInfoClick = { navController.navigate("customers/detailedCustomer/${customer.customerId}") },
                        onEditClick = {
                            navController.navigate("customers/editCustomer/${customer.customerId}")
                            expanded = false
                        },
                        onDeleteClick = {
                            selectedCustomer = customer
                            showDeleteConfirmation = true
                            expanded = false
                        },
                        expanded = expanded,
                        onExpandChange = { expanded = it}
                    )
                }
            }
        }
    }

    // Confirmation Dialog
    ConfirmationDialog(
        showDialog = showDeleteConfirmation,
        title = "Confirm Deletion",
        message = "Are you sure you want to delete \n<<${selectedCustomer?.name?.uppercase()}>>?",
        onConfirm = {
            selectedCustomer?.let {
                viewModel.deleteCustomer(it.customerId)
                navController.popBackStack()
            }
            selectedCustomer = null
            showDeleteConfirmation = false
        },
        onDismiss = {
            selectedCustomer = null
            showDeleteConfirmation = false
        }
    )
}
