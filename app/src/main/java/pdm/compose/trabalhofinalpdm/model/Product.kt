package pdm.compose.trabalhofinalpdm.model

import pdm.compose.trabalhofinalpdm.model.enums.GrainType
import pdm.compose.trabalhofinalpdm.model.enums.RoastingPoint

data class Product(
    val productId: String = "",
    val grainType: GrainType = GrainType.ARABICA_CERRADO,
    val roastingPoint: RoastingPoint = RoastingPoint.MEDIUM,
    val price: Double = 0.0,
    val isBlend: Boolean = false,
    val imageUrl: String = "" // URL for the product image
)

// Extension function to convert Customer object to a Map
fun Product.toAttributeMap(): Map<String, String> {
    return mapOf(
        "Grain Type" to grainType.name,
        "Roasting Point" to roastingPoint.name,
        "Price" to price.toString(),
        "IsBlend" to isBlend.toString(),
        "ImageUrl" to imageUrl
    )
}





