package pdm.compose.trabalhofinalpdm.domain.usecases

import android.util.Log
import pdm.compose.trabalhofinalpdm.data.DataProvider
import pdm.compose.trabalhofinalpdm.data.repository.CustomerRepository
import pdm.compose.trabalhofinalpdm.data.repository.OrderRepository
import pdm.compose.trabalhofinalpdm.util.EventBus

class DeleteCustomerAndRelatedOrdersUseCase() {
    companion object {
        private val customerRepository: CustomerRepository = DataProvider.customerRepository
        private val orderRepository: OrderRepository = DataProvider.orderRepository

        suspend operator fun invoke(customerId: String): Boolean {
            return try {
                customerRepository.delete(customerId)
                orderRepository.deleteOrdersByCustomerId(customerId)
                Log.d("DeleteCustomerAndRelatedOrdersUseCase", "Deleting customerId: $customerId")
                EventBus.notifyCustomerDeleted()
                true
            } catch (e: Exception) {
                Log.e("DeleteCustomerAndOrders", "Failed to delete", e)
                false
            }
        }
    }
}