package pdm.compose.trabalhofinalpdm.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pdm.compose.trabalhofinalpdm.data.repository.ProductRepository
import pdm.compose.trabalhofinalpdm.model.Product

class ProductViewModel (
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    init {
        viewModelScope.launch {
            _products.value = fetchProducts()
        }
    }

    private suspend fun fetchProducts(): List<Product> {
        Log.d("ProductViewModel", "Fetching all products!")
        return productRepository.getAll() ?: emptyList()
    }

    fun addProduct(product: Product){
        viewModelScope.launch {
            Log.d("ProductViewModel", "Adding: $product")
            Log.d("ProductViewModel", "Product blend: ${product.isBlend}")
            productRepository.addProduct(product)
            _products.value = fetchProducts()
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            Log.d("ProductViewModel", "Updating: $product")
            productRepository.update(product.productId, product)
            _products.value = fetchProducts()
        }
    }

    fun deleteProduct(productId: String) {
        viewModelScope.launch {
            Log.d("ProductViewModel", "Deleting product of Id: $productId")
            productRepository.delete(productId)
            _products.value = fetchProducts()
        }
    }


}