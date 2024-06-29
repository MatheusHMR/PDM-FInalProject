package pdm.compose.trabalhofinalpdm.data.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import pdm.compose.trabalhofinalpdm.model.Product

class ProductDao (
    firestore: FirebaseFirestore,
    private val customersCollection: CollectionReference
) : BaseDao<Product>(firestore, Product::class.java) {
    override val collection: CollectionReference = customersCollection

    suspend fun addProduct(product: Product) {
        val newDocRef = collection.document()
        val productWithId = product.copy(productId = newDocRef.id)
        newDocRef.set(productWithId).await()
    }

}