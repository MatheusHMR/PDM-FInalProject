package pdm.compose.trabalhofinalpdm.data.repository


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
}