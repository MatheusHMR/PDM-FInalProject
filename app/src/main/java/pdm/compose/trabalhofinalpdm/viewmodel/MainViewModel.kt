package pdm.compose.trabalhofinalpdm.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pdm.compose.trabalhofinalpdm.data.repository.CustomerRepository
import pdm.compose.trabalhofinalpdm.data.repository.OrderRepository
import pdm.compose.trabalhofinalpdm.data.repository.ProductRepository
import pdm.compose.trabalhofinalpdm.model.Customer
import pdm.compose.trabalhofinalpdm.model.Order
import pdm.compose.trabalhofinalpdm.model.Product

class MainViewModel(
//    private val productRepository: ProductRepository? = null,
    private val orderRepository: OrderRepository? = null
) : ViewModel(){

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders.asStateFlow()

    init {
        viewModelScope.launch {
//            productRepository?.let {
//                _products.value = fetchProducts()
//            }
            orderRepository?.let {
                _orders.value = fetchOrders()
            }
        }
    }

//    private suspend fun fetchProducts(): List<Product> {
//        return productRepository?.getAll() ?: emptyList()
//    }
//
//    fun addProduct(product: Product){
//        viewModelScope.launch {
//            productRepository?.addProduct(product)
//            _products.value = fetchProducts()
//        }
//    }
//
//    fun updateProduct(product: Product) {
//        viewModelScope.launch {
//            Log.d("MainViewModel", "Updating: $product")
//            productRepository?.update(product.productId, product)
//            _products.value = fetchProducts()
//        }
//    }
//
//    fun deleteProduct(productId: String) {
//        viewModelScope.launch {
//            productRepository?.delete(productId)
//            _products.value = fetchProducts()
//        }
//    }

    suspend fun fetchOrders(): List<Order> {
        return orderRepository?.getAll() ?: emptyList()
    }

    fun addOrder(order: Order) {
        viewModelScope.launch {
            orderRepository?.addOrder(order)
            _orders.value = fetchOrders()
        }
    }

    fun updateOrder(order: Order) {
        viewModelScope.launch {
            Log.d("MainViewModel", "Updating: $order")
            orderRepository?.update(order.orderId, order)
            _orders.value = fetchOrders()
        }
    }

    fun deleteOrder(orderId: String) {
        viewModelScope.launch {
            orderRepository?.delete(orderId)
            _orders.value = fetchOrders()
        }
    }
}
