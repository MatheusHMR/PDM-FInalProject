package pdm.compose.trabalhofinalpdm.model

import pdm.compose.trabalhofinalpdm.model.enums.GrainType
import pdm.compose.trabalhofinalpdm.model.enums.RoastingPoint

data class Product(
    var productId: String = "",
    var name: String = "",
    var grainType: GrainType = GrainType.ARABICA_CERRADO,
    var roastingPoint: RoastingPoint = RoastingPoint.MEDIUM,
    var price: Double = 0.0,
    var isBlend: Boolean = false,
    var imageUrl: String = "" // URL for the product image
)

fun Product.toMap(): Map<String, Any> {
    val result = mapOf(
        "productId" to productId,
        "name" to name,
        "grain Type" to grainType.name,
        "roasting Point" to roastingPoint.name,
        "price" to price,
        "isBlend" to isBlend,
        "imageUrl" to imageUrl
    )
    return result
}

fun Product.toAttributeMap(): Map<String, Any> {
    val result = mapOf(
        "name" to name,
        "grain Type" to grainType.name,
        "roasting Point" to roastingPoint.name,
        "price" to price,
        "isBlend" to isBlend,
        "imageUrl" to imageUrl
    )
    return result
}





