package pdm.compose.trabalhofinalpdm.ui.screens.orders

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import pdm.compose.trabalhofinalpdm.data.DataProvider
import pdm.compose.trabalhofinalpdm.model.Order
import pdm.compose.trabalhofinalpdm.ui.components.ConfirmationDialog
import pdm.compose.trabalhofinalpdm.ui.components.GenericListItem
import pdm.compose.trabalhofinalpdm.ui.components.TextTitle
import pdm.compose.trabalhofinalpdm.viewmodel.MainViewModel
import pdm.compose.trabalhofinalpdm.viewmodel.factory.MainViewModelFactory
import java.text.DateFormat

@Composable
fun OrderScreen(navController: NavController) {
    val viewModel: MainViewModel = viewModel(
        factory = MainViewModelFactory(
//            customerRepository = DataProvider.customerRepository,
            orderRepository = DataProvider.orderRepository
        )
    )

    val orders by viewModel.orders.collectAsState()
    Log.i("OrderScreen", "List of orders: $orders")
    var selectedOrder by remember { mutableStateOf<Order?>(null) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    // Refresh orders when the screen is recomposed
    LaunchedEffect(Unit) {
        viewModel.fetchOrders()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("orders/addOrder") }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Order")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            TextTitle("Orders")
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(orders) { order ->
                    var expanded by remember { mutableStateOf(false) }

                    GenericListItem(
                        item = order,
                        displayContent = { item ->
//                            val customers by viewModel.customers.collectAsState()
//                            val customer = customers.find { it.customerId == item.customerId }
                            Column {
                                Text(
                                    text = "Order: ${item.orderId}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = "Date: ${item.date}",
                                    fontSize = 16.sp
                                )
//                                Text(
//                                    text = "Buyer (customer): ${customer?.name ?: "Loading..."}",
//                                    fontSize = 16.sp
//                                )
                            }
                        },
                        onCardClick = { navController.navigate("orders/detailedOrder/${order.orderId}") },
                        onMoreInfoClick = { navController.navigate("customers/detailedOrder/${order.orderId}") },
                        onEditClick = {
                            navController.navigate("customers/editOrder/${order.orderId}")
                            expanded = false
                        },
                        onDeleteClick = {
                            selectedOrder = order
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

    ConfirmationDialog(
        showDialog = showDeleteConfirmation,
        title = "Confirm Deletion",
        message = "Are you sure you want to delete \n<<${selectedOrder?.orderId}>>?",
        onConfirm = {
            selectedOrder?.let {
                viewModel.deleteOrder(it.orderId)
                navController.popBackStack()
            }
            selectedOrder = null
            showDeleteConfirmation = false
        },
        onDismiss = {
            selectedOrder = null
            showDeleteConfirmation = false
        }
    )
}
