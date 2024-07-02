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
import pdm.compose.trabalhofinalpdm.model.OrderItem
import pdm.compose.trabalhofinalpdm.util.EventBus
import pdm.compose.trabalhofinalpdm.util.PriceCalculator
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderViewModel(
    private val orderRepository: OrderRepository,
    private val priceCalculator: PriceCalculator? = null
) : ViewModel(){

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders.asStateFlow()

    private val _currentOrderItems = MutableStateFlow<List<OrderItem>>(emptyList())
    val currentOrderItems: StateFlow<List<OrderItem>> = _currentOrderItems.asStateFlow()

    private val _orderItemPrices = MutableStateFlow<Map<String, Double?>>(emptyMap())
    val orderItemPrices: StateFlow<Map<String, Double?>> = _orderItemPrices.asStateFlow()


    init {
        viewModelScope.launch {
            _orders.value = fetchOrders()
            launch {
                EventBus.customerDeletedFlow.collect {
                    _orders.value = fetchOrders()
                    Log.d("OrderViewModel", "Customer Deletion Event: Fetched Orders: ${_orders.value}")
                }
            }
            launch {
                EventBus.productDeletedFlow.collect {
                    _orders.value = fetchOrders()
                    Log.d("OrderViewModel", "Product Deletion Event: Fetched Orders ${_orders.value}")
                }
            }
        }
    }

    suspend fun fetchOrders(): List<Order> {
        return orderRepository.getAll() ?: emptyList()
    }

    fun addOrder(order: Order) {
        viewModelScope.launch {
            _isLoading.value = true
            Log.d("OrderViewModel", "Adding: $order")
            orderRepository.addOrder(order)
            _orders.value = fetchOrders()
            _isLoading.value = false
            clearCurrentOrderItems()
        }
    }

    fun updateOrder(order: Order) {
        viewModelScope.launch {
            Log.d("OrderViewModel", "Updating: $order")
            orderRepository.update(order.orderId, order)
            _orders.value = fetchOrders()
        }
    }

    fun deleteOrder(orderId: String) {
        viewModelScope.launch {
            Log.d("OrderViewModel", "Deleting order of Id: $orderId")
            orderRepository.delete(orderId)
            _orders.value = fetchOrders()
        }
    }

    fun dateFormatter(timeMilliseconds: Long): String {
        val date = Date(timeMilliseconds)
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return format.format(date)
    }

    fun addOrderItem(item: OrderItem) {
        _currentOrderItems.value += item
        calculateOrderItemPrice(item) // Calculate price when adding
    }

    fun setOrderItems(items: List<OrderItem>) {
        _currentOrderItems.value = items
        _currentOrderItems.value.forEach {
            calculateOrderItemPrice(it)
        }
    }

    fun removeOrderItem(item: OrderItem) {
        _currentOrderItems.value -= item
    }

    fun clearCurrentOrderItems() {
        _currentOrderItems.value = emptyList()
    }


    fun calculateOrderItemPrice(item: OrderItem) {
        viewModelScope.launch {
            val price = priceCalculator?.calculatePrice(item)
            _orderItemPrices.value = _orderItemPrices.value.toMutableMap()
                .apply{
                    put(item.productId, price)
                }
        }
    }


}