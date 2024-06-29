package pdm.compose.trabalhofinalpdm.data.repository

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

abstract class BaseDao<T : Any> (
    protected val firestore: FirebaseFirestore,
    private val clazz: Class<T> //Passing class parameter so the BaseDao knows how to cast
) {
    abstract val collection: CollectionReference

    suspend fun add(item: T): Task<DocumentReference> {
        return try {
            Log.d("Dao", "Added ${item} of class ${clazz}")
            collection.add(item)
        } catch (e: Exception) {
            // Log the exception for debugging
            // Consider using a more specific exception type
            throw Exception("Error adding item to Firestore", e)
        }
    }

    suspend fun getAll(): List<T> {
        return try {
            collection.get().await().documents
                .mapNotNull { it.toObject(clazz) }
        } catch (e: Exception) {
            // ... error handling
            emptyList()
        }
    }

    suspend fun getOneById(id: String): DocumentSnapshot? {
        return try {
            Log.d("BaseDao", "DocumentSnapshot: ${collection.document(id).get().await()}")
            collection.document(id).get().await()
        } catch (e: Exception) {
            // ... error handling
            Log.d("BaseDao", "Couldn't find the desired item from id", e)

            null
        }
    }

    suspend fun delete(id: String): Task<Void> {
        return try {
            Log.d("BaseDao", "Trying to delete: id $id")
            collection.document(id).delete()
        } catch (e: Exception) {
            throw Exception("Error deleting item from Firestore", e)
        }
    }

    suspend fun update(id: String, item: T): Task<Void> {
        return try {
            collection.document(id).set(item)
        } catch (e: Exception) {
            // ... error handling
            throw Exception("Error updating item in Firestore", e)
        }
    }
}