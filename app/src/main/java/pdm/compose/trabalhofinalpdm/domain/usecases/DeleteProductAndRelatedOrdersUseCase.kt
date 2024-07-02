package pdm.compose.trabalhofinalpdm.domain.usecases

import android.util.Log
import pdm.compose.trabalhofinalpdm.data.DataProvider
import pdm.compose.trabalhofinalpdm.data.repository.ProductRepository
import pdm.compose.trabalhofinalpdm.data.repository.OrderRepository
import pdm.compose.trabalhofinalpdm.util.EventBus

class DeleteProductAndRelatedOrdersUseCase {
    companion object {
        private val productRepository: ProductRepository = DataProvider.productRepository
        private val orderRepository: OrderRepository = DataProvider.orderRepository

        suspend operator fun invoke(productId: String): Boolean {
            return try {
                productRepository.delete(productId)
                orderRepository.deleteOrdersByProductId(productId)
                Log.d("DeleteProductAndRelatedOrdersUseCase", "Deleting productId: $productId")
                EventBus.notifyProductDeleted()
                true
            } catch (e: Exception) {
                Log.e("DeleteCustomerAndOrders", "Failed to delete", e)
                false
            }
        }
    }
}
