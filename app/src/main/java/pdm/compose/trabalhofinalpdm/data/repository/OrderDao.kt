package pdm.compose.trabalhofinalpdm.data.repository

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import pdm.compose.trabalhofinalpdm.model.Order

class OrderDao (
    firestore: FirebaseFirestore,
    private val ordersCollection: CollectionReference,
) : BaseDao<Order>(firestore, Order::class.java) {
    override val collection: CollectionReference = ordersCollection

    suspend fun addOrder(order: Order) {
        val newDocRef = collection.document()
        val orderWithId = order.copy(orderId = newDocRef.id)
        newDocRef.set(orderWithId).await()
    }

    suspend fun getOrdersByCustomerId(customerId: String): List<Order> {
        return try {
            collection.whereEqualTo("customerId", customerId).get().await()
                .documents.mapNotNull { it.toObject(Order::class.java) }
        } catch (e: Exception) {
            Log.e("OrderDao", "Failed to get orders for customerId: $customerId", e)
            emptyList()
        }
    }

    suspend fun getOrdersByProductId(productId: String): List<Order> {
        return try {
            val ordersWithProduct = mutableListOf<Order>()
            val allOrders = collection.get().await().documents.mapNotNull { it.toObject(Order::class.java) }

            for (order in allOrders) {
                val itemsSize = order.items.size // Obtém o tamanho do array 'items'

                for (i in 0 until itemsSize) { // Itera pelo array usando o tamanho
                    val productIdInItem = order.items[i].productId

                    if (productIdInItem == productId) {
                        ordersWithProduct.add(order)
                        break // Se encontrar o produto, passa para o próximo pedido
                    }
                }
            }

            Log.d("OrderDao", "Orders of productId: $productId: $ordersWithProduct")
            ordersWithProduct
        } catch (e: Exception) {
            Log.e("OrderDao", "Failed to get orders for productId: $productId", e)
            emptyList()
        }
    }
}