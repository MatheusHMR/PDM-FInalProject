package pdm.compose.trabalhofinalpdm.ui.screens.orders

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import pdm.compose.trabalhofinalpdm.viewmodel.CustomerViewModel
import pdm.compose.trabalhofinalpdm.viewmodel.OrderViewModel
import pdm.compose.trabalhofinalpdm.viewmodel.ProductViewModel
import pdm.compose.trabalhofinalpdm.viewmodel.factory.CustomerViewModelFactory
import pdm.compose.trabalhofinalpdm.viewmodel.factory.OrderViewModelFactory
import pdm.compose.trabalhofinalpdm.viewmodel.factory.ProductViewModelFactory

@Composable
fun OrderScreen(navController: NavController) {

    val orderViewModel: OrderViewModel = viewModel(
        factory = OrderViewModelFactory(
            orderRepository = DataProvider.orderRepository
        )
    )
    val customerViewModel: CustomerViewModel = viewModel(
        factory = CustomerViewModelFactory(
            customerRepository = DataProvider.customerRepository
        )
    )

    val orders by orderViewModel.orders.collectAsState()
    val isLoading by orderViewModel.isLoading.collectAsState()
    val customers by customerViewModel.customers.collectAsState()

    Log.i("OrderScreen", "Orders: $orders")
    var selectedOrder by remember { mutableStateOf<Order?>(null) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }

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
            if(isLoading){
                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(orders) { order ->
                        var expanded by remember { mutableStateOf(false) }

                        GenericListItem(
                            item = order,
                            displayContent = { item ->
                                val customer = customers.find { it.customerId == item.customerId}
                                Column {
                                    Text(
                                        text = "Order: ${item.orderId}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                    Text(
                                        text = "Date: ${orderViewModel.dateFormatter(item.date)}",
                                        fontSize = 16.sp
                                    )
                                    Text(
                                        text = "Buyer (customer): ${customer?.name ?: "Loading..."}",
                                        fontSize = 16.sp
                                    )
                                }
                            },
                            onCardClick = { navController.navigate("orders/detailedOrder/${order.orderId}") },
                            onMoreInfoClick = { navController.navigate("orders/detailedOrder/${order.orderId}") },
                            onEditClick = {
                                navController.navigate("orders/editOrder/${order.orderId}")
                                Log.d("OrderScreen", "Order that's going to be edited: $order")
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
    }

    ConfirmationDialog(
        showDialog = showDeleteConfirmation,
        title = "Confirm Deletion",
        message = "Are you sure you want to delete \n<<${selectedOrder?.orderId}>>?",
        onConfirm = {
            selectedOrder?.let {
                orderViewModel.deleteOrder(it.orderId)
                navController.navigate("orders")
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
