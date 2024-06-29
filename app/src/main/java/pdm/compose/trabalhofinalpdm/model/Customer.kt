package pdm.compose.trabalhofinalpdm.model

data class Customer(
    val customerId: String = "",
    val cpf: String = "",
    val name: String = "",
    val phone: String = "",
    val address: String = "",
    val instagram: String = ""
)

// Extension function to convert Customer object to a Map
fun Customer.toAttributeMap(): Map<String, String> {
    return mapOf(
        "Cpf" to cpf,
        "Name" to name,
        "Phone" to phone,
        "Address" to address,
        "Instagram" to instagram
    )
}