package pdm.compose.trabalhofinalpdm.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.CollectionReference
import pdm.compose.trabalhofinalpdm.data.repository.CustomerDao
import pdm.compose.trabalhofinalpdm.data.repository.CustomerRepository
import pdm.compose.trabalhofinalpdm.data.repository.OrderDao
import pdm.compose.trabalhofinalpdm.data.repository.OrderRepository
import pdm.compose.trabalhofinalpdm.data.repository.ProductDao
import pdm.compose.trabalhofinalpdm.data.repository.ProductRepository

object DataProvider {
    val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    private val customersCollection: CollectionReference by lazy { firestore.collection("customers") }
    val customerDao: CustomerDao by lazy { CustomerDao(firestore, customersCollection) }
    val customerRepository: CustomerRepository by lazy { CustomerRepository(customerDao) }

    private val productsCollection: CollectionReference by lazy { firestore.collection("products") }
    val productDao: ProductDao by lazy { ProductDao(firestore, productsCollection) }
    val productRepository: ProductRepository by lazy { ProductRepository(productDao) }

    private val ordersCollection: CollectionReference by lazy { firestore.collection("orders") }
    val orderDao: OrderDao by lazy { OrderDao(firestore, ordersCollection) }
    val orderRepository: OrderRepository by lazy { OrderRepository(orderDao, customerRepository, productRepository) }
}