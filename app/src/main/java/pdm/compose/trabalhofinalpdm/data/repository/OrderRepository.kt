package pdm.compose.trabalhofinalpdm.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import pdm.compose.trabalhofinalpdm.model.Order
import pdm.compose.trabalhofinalpdm.model.Product

class OrderRepository(
    private val orderDao: OrderDao,
    private val customerRepository: CustomerRepository,
    private val productRepository: ProductRepository
) : BaseRepository<Order>() {
    override val dao: BaseDao<Order> = orderDao

    suspend fun addOrder(order: Order): Result<Unit> {
        return runCatching {
            // 1. Check if customer exists
            val customerExists = customerRepository.getOneById(order.customerId) != null
            if (!customerExists) {
                return Result.failure(Exception("Customer does not exist"))
            }

            for (item in order.items) {
                val product = productRepository.getOneById(item.productId)?.toObject(Product::class.java)
                if (product == null) {
                    return Result.failure(Exception("Product with ID ${item.productId} does not exist"))
                }
            }

            // 3. If all checks pass, add the order
            orderDao.addOrder(order)
        }
    }

    // ... other functions for fetching, updating, and deleting orders
}