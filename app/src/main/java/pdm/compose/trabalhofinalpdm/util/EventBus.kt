package pdm.compose.trabalhofinalpdm.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

object EventBus {
    private val _productDeletedFlow = MutableSharedFlow<Unit>()
    val productDeletedFlow = _productDeletedFlow.asSharedFlow()

    private val _customerDeletedFlow = MutableSharedFlow<Unit>()
    val customerDeletedFlow = _customerDeletedFlow.asSharedFlow()

    fun notifyProductDeleted() {
        CoroutineScope(Dispatchers.IO).launch {
            _productDeletedFlow.emit(Unit)
        }
    }

    fun notifyCustomerDeleted() {
        CoroutineScope(Dispatchers.IO).launch {
            _customerDeletedFlow.emit(Unit)
        }
    }
}




