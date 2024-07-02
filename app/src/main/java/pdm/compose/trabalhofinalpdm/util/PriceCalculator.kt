package pdm.compose.trabalhofinalpdm.util

import android.util.Log
import pdm.compose.trabalhofinalpdm.data.repository.ProductRepository
import pdm.compose.trabalhofinalpdm.model.OrderItem

class PriceCalculator(private val productRepository: ProductRepository) {
    suspend fun calculatePrice(item: OrderItem): Double {
        val product = productRepository.getProductById(item.productId)
        if(product == null)
            Log.e("PriceCalculator", "Product is null")
        return product?.price?.times(item.quantity) ?: 0.0 // Calculate price or return 0 if product not found
    }

}