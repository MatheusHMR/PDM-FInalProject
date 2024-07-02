package pdm.compose.trabalhofinalpdm.data.repository

import android.util.Log
import pdm.compose.trabalhofinalpdm.model.Customer

class CustomerRepository (
    private val customerDao: CustomerDao // Inject CustomerDao directly
): BaseRepository<Customer>() {
    override val dao: BaseDao<Customer> = customerDao // Implement the abstract property

    suspend fun addCustomer(customer: Customer) : Boolean {
        return try {
            customerDao.addCustomer(customer)
            true
        } catch (e: Exception) {
            Log.e("CustomerRepository", "Failed to add the Customer: $customer")
            false
        }
    }
}
