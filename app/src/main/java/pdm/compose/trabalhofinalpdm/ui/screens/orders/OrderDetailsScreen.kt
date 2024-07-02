package pdm.compose.trabalhofinalpdm.ui.screens.orders

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import pdm.compose.trabalhofinalpdm.data.DataProvider
import pdm.compose.trabalhofinalpdm.ui.components.GenericAttributeCard
import pdm.compose.trabalhofinalpdm.ui.components.TextLabel
import pdm.compose.trabalhofinalpdm.ui.components.TextTitle
import pdm.compose.trabalhofinalpdm.viewmodel.CustomerViewModel
import pdm.compose.trabalhofinalpdm.viewmodel.OrderViewModel
import pdm.compose.trabalhofinalpdm.viewmodel.ProductViewModel
import pdm.compose.trabalhofinalpdm.viewmodel.factory.CustomerViewModelFactory
import pdm.compose.trabalhofinalpdm.viewmodel.factory.OrderViewModelFactory
import pdm.compose.trabalhofinalpdm.viewmodel.factory.ProductViewModelFactory

@Composable
fun OrderDetailsScreen(
    orderId: String,
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

    val orders by orderViewModel.orders.collectAsState()
    val order = orders.find { it.orderId == orderId }
    val customers by customerViewModel.customers.collectAsState()
    val products by productViewModel.products.collectAsState()

    Log.d("OrderDetailsScreen", "Order Id: $orderId")

    Scaffold(
        floatingActionButton = {
            if (order != null) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate("orders/editOrder/$orderId") // Adjust navigation route
                        Log.d("OrdersDetailsScreen","Order that's going to be edited: ${order.orderId}")
                    }
                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Product")
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
                    onClick = { navController.navigate("orders") }, // Adjust navigation route
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to Orders List"
                    )
                }
            }

            if (order != null) {
                val customer = customers.find { it.customerId == order.customerId}
                val basicAttributesMap = mapOf(
                   "OrderId" to orderId,
                    "Buyer (Costumer)" to customer?.name,
                    "Date" to orderViewModel.dateFormatter(order.date),
                )
                TextTitle(
                    text = "Order Details",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                Column(
                    modifier = Modifier.padding(4.dp)
                        .verticalScroll(state = ScrollState(0)),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    basicAttributesMap.forEach { (name, value) ->
                        Column(
                            modifier = Modifier.padding(4.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            TextLabel(text = name)
                            GenericAttributeCard(value = value.toString())
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    TextLabel(text = "Order Items", modifier = Modifier.padding(horizontal = 14.dp))
                    order.items.forEach { orderItem ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                                .background(MaterialTheme.colorScheme.background),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.onSecondary,
                                disabledContainerColor = MaterialTheme.colorScheme.tertiary,
                                disabledContentColor = MaterialTheme.colorScheme.onTertiary
                            )
                        ) {
                            val productName =
                                (products.find { it.productId == orderItem.productId })?.name
                            Column(
                                Modifier.padding(8.dp)
                            ) {
                                Text("Product: $productName")
                                Text("Quantity: ${orderItem.quantity}")
                            }
                        }
                    }
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

