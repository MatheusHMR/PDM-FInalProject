package pdm.compose.trabalhofinalpdm.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pdm.compose.trabalhofinalpdm.data.repository.CustomerRepository
import pdm.compose.trabalhofinalpdm.model.Customer

class CustomerViewModel(
    private val customerRepository: CustomerRepository
) : ViewModel() {
    private val _customers = MutableStateFlow<List<Customer>>(emptyList())
    val customers: StateFlow<List<Customer>> = _customers.asStateFlow()

    init {
        viewModelScope.launch {
            _customers.value = fetchCustomers()
        }
    }

    private suspend fun fetchCustomers(): List<Customer> {
        Log.d("CustomerViewModel", "Fetching customers!")
        return customerRepository.getAll()
    }

    suspend fun getCustomerById(customerId: String): Customer? {
        Log.d("CustomerViewModel", "Searching for Id: $customerId")
        return customerRepository.getOneById(customerId)?.toObject(Customer::class.java)
    }

    fun addCustomer(customer: Customer) {
        viewModelScope.launch {
            Log.d("CustomerViewModel", "Adding: $customer")
            customerRepository.addCustomer(customer)
            _customers.value = fetchCustomers()
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
            customerRepository.delete(customerId)
            _customers.value = fetchCustomers()
        }
    }
}