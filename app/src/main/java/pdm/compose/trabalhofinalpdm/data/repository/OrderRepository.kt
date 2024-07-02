package pdm.compose.trabalhofinalpdm.data.repository

import android.util.Log
import pdm.compose.trabalhofinalpdm.model.Order

class OrderRepository(
    private val orderDao: OrderDao,
    private val customerRepository: CustomerRepository,
    private val productRepository: ProductRepository
) : BaseRepository<Order>() {
    override val dao: BaseDao<Order> = orderDao

    suspend fun addOrder(order: Order): Boolean {
        return try {
            orderDao.addOrder(order)
            true
        } catch (e: Exception) {
            Log.e("CustomerRepository", "Failed to add the Customer: $order")
            false
        }
    }

    suspend fun deleteOrdersByCustomerId(customerId: String): Boolean {
        return try {
            val ordersToDelete = orderDao.getOrdersByCustomerId(customerId)
            for (order in ordersToDelete) {
                orderDao.delete(order.orderId)
            }
            true
        } catch (e: Exception) {
            Log.e("OrderRepository", "Failed to delete orders for customerId: $customerId", e)
            false
        }
    }

    suspend fun deleteOrdersByProductId(productId: String): Boolean {
        return try {
            val ordersToDelete = orderDao.getOrdersByProductId(productId)
            for (order in ordersToDelete) {
                orderDao.delete(order.orderId)
            }
            Log.d("OrderRepository", "Orders to delete: $ordersToDelete")
            true
        } catch (e: Exception) {
            Log.e("OrderRepository", "Failed to delete orders for productId: $productId", e)
            false
        }
    }

}