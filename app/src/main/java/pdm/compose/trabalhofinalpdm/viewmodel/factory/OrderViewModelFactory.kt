package pdm.compose.trabalhofinalpdm.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pdm.compose.trabalhofinalpdm.data.DataProvider
import pdm.compose.trabalhofinalpdm.data.repository.OrderRepository
import pdm.compose.trabalhofinalpdm.util.PriceCalculator
import pdm.compose.trabalhofinalpdm.viewmodel.OrderViewModel

class OrderViewModelFactory(
    private val orderRepository: OrderRepository,
    private val priceCalculator: PriceCalculator? = null
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OrderViewModel(
                orderRepository,
                priceCalculator ?: PriceCalculator(DataProvider.productRepository)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}