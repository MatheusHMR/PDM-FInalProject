package pdm.compose.trabalhofinalpdm.model

data class Order(
    val orderId: String = "",
    val customerId: String = "",
    val date: Long = System.currentTimeMillis(),
    val items: List<OrderItem> = emptyList()
)

data class OrderItem(
    val productId: String = "",
    val quantity: Int = 1
)