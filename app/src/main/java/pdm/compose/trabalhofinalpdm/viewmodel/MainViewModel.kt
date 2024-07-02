package pdm.compose.trabalhofinalpdm.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pdm.compose.trabalhofinalpdm.data.repository.OrderRepository
import pdm.compose.trabalhofinalpdm.model.Order

class MainViewModel(
    private val orderRepository: OrderRepository? = null
) : ViewModel(){

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders.asStateFlow()

    init {
        viewModelScope.launch {

            orderRepository?.let {
                _orders.value = fetchOrders()
            }
        }
    }

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
