package pdm.compose.trabalhofinalpdm.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pdm.compose.trabalhofinalpdm.data.DataProvider.productRepository
import pdm.compose.trabalhofinalpdm.data.repository.CustomerRepository
import pdm.compose.trabalhofinalpdm.data.repository.OrderRepository
import pdm.compose.trabalhofinalpdm.data.repository.ProductRepository
//import pdm.compose.trabalhofinalpdm.data.repository.OrderRepository // Assuming you have an OrderRepository
//import pdm.compose.trabalhofinalpdm.data.repository.ProductRepository // Assuming you have a ProductRepository
import pdm.compose.trabalhofinalpdm.viewmodel.MainViewModel

class MainViewModelFactory(
    private val customerRepository: CustomerRepository? = null,
    private val productRepository: ProductRepository? = null,
    private val orderRepository: OrderRepository? = null
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                customerRepository = customerRepository,
                productRepository = productRepository,
                orderRepository = orderRepository
                ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}