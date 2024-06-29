package pdm.compose.trabalhofinalpdm.data.repository

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
}