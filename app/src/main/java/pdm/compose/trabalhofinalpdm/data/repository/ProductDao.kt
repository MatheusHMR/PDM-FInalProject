package pdm.compose.trabalhofinalpdm.data.repository

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import pdm.compose.trabalhofinalpdm.model.Product
import pdm.compose.trabalhofinalpdm.model.toMap

class ProductDao (
    firestore: FirebaseFirestore,
    private val customersCollection: CollectionReference
) : BaseDao<Product>(firestore, Product::class.java) {
    override val collection: CollectionReference = customersCollection

    suspend fun addProduct(product: Product) {
        val newDocRef = collection.document()
        val productWithId = product.copy(productId = newDocRef.id)
        val productMap = productWithId.toMap()
        newDocRef.set(productWithId).addOnSuccessListener {
            Log.d("ProductRepository", "Product added with ID: ${productWithId.productId}")
            Log.d("ProductRepository", "Product added with blend: ${productWithId.isBlend}")
        }
            .addOnFailureListener { e ->
                Log.w("ProductRepository", "Error adding product", e)
            }
            .await()
    }

}