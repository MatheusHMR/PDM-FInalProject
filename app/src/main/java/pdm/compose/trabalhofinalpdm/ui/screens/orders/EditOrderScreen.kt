package pdm.compose.trabalhofinalpdm.ui.screens.orders

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import pdm.compose.trabalhofinalpdm.data.DataProvider
import pdm.compose.trabalhofinalpdm.model.Customer
import pdm.compose.trabalhofinalpdm.model.Order
import pdm.compose.trabalhofinalpdm.model.OrderItem
import pdm.compose.trabalhofinalpdm.model.Product
import pdm.compose.trabalhofinalpdm.ui.components.CustomOutlinedTextField
import pdm.compose.trabalhofinalpdm.ui.components.DropdownMenuComponent
import pdm.compose.trabalhofinalpdm.ui.components.GenericListItem
import pdm.compose.trabalhofinalpdm.ui.components.TextTitle
import pdm.compose.trabalhofinalpdm.viewmodel.CustomerViewModel
import pdm.compose.trabalhofinalpdm.viewmodel.OrderViewModel
import pdm.compose.trabalhofinalpdm.viewmodel.ProductViewModel
import pdm.compose.trabalhofinalpdm.viewmodel.factory.CustomerViewModelFactory
import pdm.compose.trabalhofinalpdm.viewmodel.factory.OrderViewModelFactory
import pdm.compose.trabalhofinalpdm.viewmodel.factory.ProductViewModelFactory
import java.util.Calendar
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "MutableCollectionMutableState")
@Composable
fun EditOrderScreen(
    orderId: String, // Pass the orderId to the screen
    navController: NavController
) {
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
    val productViewModel: ProductViewModel = viewModel(
        factory = ProductViewModelFactory(
            productRepository = DataProvider.productRepository
        )
    )

    val customers by customerViewModel.customers.collectAsState()
    val products by productViewModel.products.collectAsState()

    // Find the order to edit
    val orderToEdit = orderViewModel.orders.collectAsState().value.find { it.orderId == orderId }
    orderToEdit?.items?.let { orderViewModel.setOrderItems(it) }

    var selectedCustomer by remember { mutableStateOf<Customer?>(orderToEdit?.let { customer ->
        customers.find { it.customerId == customer.customerId }
    }) }

    val orderItems by orderViewModel.currentOrderItems.collectAsState()
    val orderItemPrices by orderViewModel.orderItemPrices.collectAsState()

    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    var quantity by remember { mutableStateOf("1") }
    var currentStep by remember { mutableStateOf(1) }
    var selectedDate by remember { mutableStateOf(orderToEdit?.date ?: System.currentTimeMillis()) }

    val customerOptions = customers.map { "${it.name} - ${it.cpf}" }
    val productOptions = products.map { it.name }
    val productImages = products.map { it.imageUrl }


    Scaffold(
        floatingActionButton = {
            if (currentStep == 1) {
                FloatingActionButton(onClick = {
                    currentStep = 2
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Continue Order"
                    )
                }
            } else {
                FloatingActionButton(onClick = {
                    // Update the order in your ViewModel
                    orderToEdit?.let { existingOrder ->
                        val updatedOrder = existingOrder.copy(
                            customerId = selectedCustomer?.customerId ?: "",
                            items = orderItems.toList(),
                            date = selectedDate
                        )
                        orderViewModel.updateOrder(updatedOrder)
                    }
                    navController.navigate("orders")
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Finish Order"
                    )
                }
            }
        }
    ) {
        if (currentStep == 1) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(onClick = { navController.navigate("orders") }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back to Orders List"
                        )
                    }
                    TextTitle("Edit Order", modifier = Modifier.padding(horizontal = 60.dp, vertical = 8.dp))
                }

                Spacer(modifier = Modifier.padding(8.dp))

                OutlinedTextField(
                    value = orderId ?: "", // Display the orderId
                    onValueChange = { /* Do nothing, field is disabled */ },
                    label = { Text("Order ID") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = false // Disable the field
                )

                Spacer(modifier = Modifier.height(16.dp))

                Spacer(modifier = Modifier.height(16.dp))

                DropdownMenuComponent(
                    label = "Select Product",
                    selectedOption = selectedProduct?.name ?: "Select Product",
                    options = productOptions,
                    onSelectionChange = { selectedOption ->
                        selectedProduct = products.find { it.name == selectedOption }
                    },
                    imageUrls = productImages
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomOutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = "Quantity",
                    modifier = Modifier.fillMaxWidth(),
                    keyboardType = KeyboardType.Number
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        selectedProduct?.let { product ->
                            val newItem = OrderItem(product.productId, quantity.toInt())
                            orderViewModel.addOrderItem(newItem)
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Add to Shopping Cart")
                }

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn {
                    items(orderItems) { orderItem ->
                        var expanded by remember { mutableStateOf(false) }
                        GenericListItem(
                            item = orderItem,
                            displayContent = { item ->
                                val product = products.find { it.productId == item.productId }
                                Column {
                                    Text("Product: ${product?.name}")
                                    Text("Quantity: ${item.quantity}")
                                }
                            },
                            onCardClick = {},
                            onMoreInfoClick = null,
                            onEditClick = null,
                            onDeleteClick = {
                                orderViewModel.removeOrderItem(orderItem)
                            },
                            expanded = expanded,
                            onExpandChange = { expanded = it }
                        )
                    }
                }

            }
        } else {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(onClick = { currentStep = 1 }) { // Adjust navigation route
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back to Order Item Selection"
                        )
                    }
                }
                TextTitle("Finalize Order Edition")

                DropdownMenuComponent(
                    label = "Select Customer",
                    selectedOption = selectedCustomer?.let { "${it.name} - ${it.cpf}" } ?: "Select Customer",
                    options = customerOptions,
                    onSelectionChange = { selectedOption ->
                        selectedCustomer = customers.find { "${it.name} - ${it.cpf}" == selectedOption }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn {
                    items(orderItems) { orderItem ->
                        var expanded by remember { mutableStateOf(false) }
                        val calculatedPrice = orderItemPrices[orderItem.productId]
                        Log.d("EditOrderScreen", "")

                        GenericListItem(
                            item = orderItem,
                            displayContent = { item ->
                                val product = products.find { it.productId == item.productId }
                                Column {
                                    Text("Product: ${product?.name}")
                                    Text("Quantity: ${item.quantity}")
                                    // Conditionally display the price
                                    if (calculatedPrice != null) {
                                        Text("Total price: $${String.format(Locale.getDefault(),"%.2f", calculatedPrice)}")
                                    } else {
                                        CircularProgressIndicator() // Show a loading indicator while calculating
                                    }
                                }
                            },
                            onCardClick = {},
                            onMoreInfoClick = null,
                            onEditClick = null,
                            onDeleteClick = {
                                orderViewModel.removeOrderItem(orderItem)
                            },
                            expanded = expanded,
                            onExpandChange = { expanded = it }
                        )
                    }
                }
            }
        }
    }
}