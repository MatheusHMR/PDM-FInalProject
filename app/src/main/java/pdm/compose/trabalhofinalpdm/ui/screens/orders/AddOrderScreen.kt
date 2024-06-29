//package pdm.compose.trabalhofinalpdm.ui.screens.orders
//
//import android.annotation.SuppressLint
//import android.util.Log
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//import pdm.compose.trabalhofinalpdm.data.DataProvider
//import pdm.compose.trabalhofinalpdm.model.Customer
//import pdm.compose.trabalhofinalpdm.model.Order
//import pdm.compose.trabalhofinalpdm.model.OrderItem
//import pdm.compose.trabalhofinalpdm.model.Product
//import pdm.compose.trabalhofinalpdm.ui.components.CustomOutlinedTextField
//import pdm.compose.trabalhofinalpdm.ui.components.DropdownMenuComponent
//import pdm.compose.trabalhofinalpdm.ui.components.TextTitle
//import pdm.compose.trabalhofinalpdm.viewmodel.MainViewModel
//import pdm.compose.trabalhofinalpdm.viewmodel.factory.MainViewModelFactory
//
//@SuppressLint("MutableCollectionMutableState", "UnusedMaterial3ScaffoldPaddingParameter")
//@Composable
//fun AddOrderScreen(navController: NavController) {
//
//    val viewModel: MainViewModel = viewModel(
//        factory = MainViewModelFactory(
//            productRepository = DataProvider.productRepository,
//            orderRepository = DataProvider.orderRepository,
//            customerRepository = DataProvider.customerRepository)
//    )
//
//    val customers by viewModel.customers.collectAsState()
//    Log.i("AddOrderScreen", "Customers: $customers")
//    val products by viewModel.products.collectAsState()
//    Log.i("AddOrderScreen", "Products: $products")
//
//
//    var selectedCustomer by remember { mutableStateOf<Customer?>(null) }
//    var selectedProduct by remember { mutableStateOf<Product?>(null) }
//    var quantity by remember { mutableStateOf("1") }
//    var orderItems by remember { mutableStateOf(mutableListOf<OrderItem>()) }
//
//    val customerOptions = customers.map { "${it.name} - ${it.cpf}" }
//    val productOptions = products.map { it.productId }
//    val productImages = products.map { it.imageUrl }
//    Log.i("AddOrderScreen", "Customer options: $customerOptions")
//
//    Scaffold(
//        floatingActionButton = {
//            FloatingActionButton(onClick = {
//                val newOrder = Order(
//                    customerId = selectedCustomer?.customerId ?: "",
//                    items = orderItems.toList()
//                )
//                viewModel.addOrder(newOrder)
//                navController.popBackStack()
//            }) {
//                Icon(imageVector = Icons.Default.Add, contentDescription = "Finish Order")
//            }
//        }
//    ) {
//        Column(
//            modifier = Modifier
//                .padding(16.dp)
//        ) {
//            TextTitle("Add New Order")
//
//            DropdownMenuComponent(
//                label = "Select Customer",
//                selectedOption = selectedCustomer?.let { "${it.name} - ${it.cpf}" } ?: "Select Customer",
//                options = customerOptions,
//                onSelectionChange = { selectedCustomer = customers[customerOptions.indexOf(it)] }
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            DropdownMenuComponent(
//                label = "Select Product",
//                selectedOption = selectedProduct?.productId ?: "Select Product",
//                options = productOptions,
//                onSelectionChange = { selectedProduct = products[productOptions.indexOf(it)] },
//                imageUrls = productImages
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            CustomOutlinedTextField(
//                value = quantity,
//                onValueChange = { quantity = it },
//                label = "Quantity",
//                modifier = Modifier.fillMaxWidth(),
//                keyboardType = KeyboardType.Number
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Button(
//                onClick = {
//                selectedProduct?.let {
//                    orderItems.add(OrderItem(it.productId, quantity.toInt()))
//                }
//            }) {
//                Text("Add to Shopping Cart")
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            LazyColumn {
//                items(orderItems) { orderItem ->
//                    Text("Product ID: ${orderItem.productId}, Quantity: ${orderItem.quantity}")
//                }
//            }
//        }
//    }
//}

package pdm.compose.trabalhofinalpdm.ui.screens.orders

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
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
import pdm.compose.trabalhofinalpdm.viewmodel.MainViewModel
import pdm.compose.trabalhofinalpdm.viewmodel.factory.MainViewModelFactory

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddOrderScreen(navController: NavController) {
    val viewModel: MainViewModel = viewModel(
        factory = MainViewModelFactory(
            productRepository = DataProvider.productRepository,
            orderRepository = DataProvider.orderRepository,
            customerRepository = DataProvider.customerRepository
        )
    )

    val customers by viewModel.customers.collectAsState()
    val products by viewModel.products.collectAsState()

    var selectedCustomer by remember { mutableStateOf<Customer?>(null) }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    var quantity by remember { mutableStateOf("1") }
    var orderItems by remember { mutableStateOf(mutableListOf<OrderItem>()) }
    var currentStep by remember { mutableStateOf(1) }

    val customerOptions = customers.map { "${it.name} - ${it.cpf}" }
    val productOptions = products.map { it.productId }
    val productImages = products.map { it.imageUrl }

    Scaffold(
        floatingActionButton = {
            if (currentStep == 1) {
                FloatingActionButton(onClick = {
                    currentStep = 2
                }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Continue Order")
                }
            } else {
                FloatingActionButton(onClick = {
                    val newOrder = Order(
                        customerId = selectedCustomer?.customerId ?: "",
                        items = orderItems.toList()
                    )
                    viewModel.addOrder(newOrder)
                    navController.popBackStack("orders", inclusive = false)
                }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Finish Order")
                }
            }
        }
    ) {
        if (currentStep == 1) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                TextTitle("Add New Order")

                DropdownMenuComponent(
                    label = "Select Product",
                    selectedOption = selectedProduct?.productId ?: "Select Product",
                    options = productOptions,
                    onSelectionChange = { selectedOption ->
                        selectedProduct = products.find { it.productId == selectedOption }
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
                        selectedProduct?.let {
                            orderItems.add(OrderItem(it.productId, quantity.toInt()))
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
                                Column {
                                    Text("Product ID: ${item.productId}")
                                    Text("Quantity: ${item.quantity}")
                                }
                            },
                            onCardClick = {},
                            onMoreInfoClick = null,
                            onEditClick = null,
                            onDeleteClick = {
                                orderItems.remove(orderItem)
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
                TextTitle("Finalize Order")

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
                        GenericListItem(
                            item = orderItem,
                            displayContent = { item ->
                                Column {
                                    Text("Product ID: ${item.productId}")
                                    Text("Quantity: ${item.quantity}")
                                }
                            },
                            onCardClick = {},
                            onMoreInfoClick = null,
                            onEditClick = null,
                            onDeleteClick = {
                                orderItems.remove(orderItem)
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
