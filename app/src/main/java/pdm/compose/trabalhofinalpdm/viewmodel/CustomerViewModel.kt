package pdm.compose.trabalhofinalpdm.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pdm.compose.trabalhofinalpdm.data.repository.CustomerRepository
import pdm.compose.trabalhofinalpdm.domain.usecases.DeleteCustomerAndRelatedOrdersUseCase
import pdm.compose.trabalhofinalpdm.model.Customer

class CustomerViewModel(
    private val customerRepository: CustomerRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _customers = MutableStateFlow<List<Customer>>(emptyList())
    val customers: StateFlow<List<Customer>> = _customers.asStateFlow()

    private val _customerDeletedEvent = MutableSharedFlow<Unit>()
    val customerDeletedEvent: SharedFlow<Unit> = _customerDeletedEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            _customers.value = fetchCustomers()
        }
    }

    private suspend fun fetchCustomers(): List<Customer> {
        Log.d("CustomerViewModel", "Fetching customers!")
        return customerRepository.getAll()
    }

    fun addCustomer(customer: Customer) {
        viewModelScope.launch {
            _isLoading.value = true
            Log.d("CustomerViewModel", "Adding: $customer")
            val success = customerRepository.addCustomer(customer)
            if(success) {
                _customers.value = fetchCustomers()
            }
            _isLoading.value = false
        }
    }

    fun updateCustomer(customer: Customer) {
        viewModelScope.launch {
            Log.d("CustomerViewModel", "Updating: $customer")
            customerRepository.update(customer.customerId, customer)
            _customers.value = fetchCustomers()
        }
    }

    fun deleteCustomer(customerId: String) {
        viewModelScope.launch {
            Log.d("CustomerViewModel", "Deleting customer of Id: $customerId")
            val deleted = DeleteCustomerAndRelatedOrdersUseCase.invoke(customerId)
            if(deleted) {
                _customers.value = fetchCustomers()
            }
        }
    }
}