package pdm.compose.trabalhofinalpdm.data.repository

import pdm.compose.trabalhofinalpdm.model.Customer

class CustomerRepository (
    private val customerDao: CustomerDao // Inject CustomerDao directly
): BaseRepository<Customer>() {
    override val dao: BaseDao<Customer> = customerDao // Implement the abstract property

    suspend fun addCustomer(customer: Customer) {
        try {
            customerDao.addCustomer(customer)
        } catch (e: Exception) {
            throw e
        }
    }
}