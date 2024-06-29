package pdm.compose.trabalhofinalpdm.data.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import pdm.compose.trabalhofinalpdm.model.Customer

class CustomerDao (
    firestore: FirebaseFirestore,
    private val customersCollection: CollectionReference
) : BaseDao<Customer>(firestore, Customer::class.java) {
    override val collection: CollectionReference = customersCollection

    suspend fun addCustomer(customer: Customer) {
        val newDocRef = collection.document()
        val customerWithId = customer.copy(customerId = newDocRef.id)
        newDocRef.set(customerWithId).await()
    }

}