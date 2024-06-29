package pdm.compose.trabalhofinalpdm.data.repository


import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.tasks.await
import java.lang.Exception
/*
* protected: I'm using protected to allow access to the dao property within the BaseRepository
* class and its subclasses (concrete repositories like CustomerRepository).
* This is necessary because the repository functions need to interact with the DAO.
*
* abstract val: I'm using abstract val because:
*
* abstract: The BaseRepository doesn't provide a concrete implementation for the dao property.
* Each concrete repository (like CustomerRepository, OrderRepository and ProductRepository) will provide its own specific DAO instance.
*
*
* */


abstract class BaseRepository<T : Any> {
    protected abstract val dao: BaseDao<T>

    suspend fun add(item: T) {
        try {
            dao.add(item).await()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getAll(): List<T> {
        return dao.getAll() // No need for try/catch here, as BaseDao already handles it
    }

    suspend fun getOneById(id: String): DocumentSnapshot? {
        return dao.getOneById(id) // No need for try/catch here
    }

    suspend fun delete(id: String) {
        try {
            dao.delete(id).await()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun update(id: String, item: T) {
        try {
            dao.update(id, item).await()
        } catch (e: Exception) {
            throw e
        }
    }
}