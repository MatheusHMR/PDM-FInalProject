package pdm.compose.trabalhofinalpdm.data.repository


import android.util.Log
import pdm.compose.trabalhofinalpdm.model.Product

class ProductRepository (
    private val productDao: ProductDao // Inject CustomerDao directly
): BaseRepository<Product>() {
    override val dao: BaseDao<Product> = productDao // Implement the abstract property

    suspend fun addProduct(product: Product) {
        try {
            productDao.addProduct(product)
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getProductById(productId: String) : Product? {
        try {
            val snapshot = productDao.getOneById(productId)
            snapshot?.let {
                return snapshot.toObject(Product::class.java)
            }.run {
                Log.e("ProductRepository", "Could not find the desired product of productId: $productId")
                return null
            }
        } catch (e: Exception) {
            throw e
        }
    }
}