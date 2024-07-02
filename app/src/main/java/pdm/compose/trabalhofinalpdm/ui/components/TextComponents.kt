package pdm.compose.trabalhofinalpdm.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextTitle(
    text: String = "Empty",
    color: Color = MaterialTheme.colorScheme.onSecondary,
    fontSize: TextUnit = 32.sp,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(32.dp)
){
    Text(
        text = text,
        modifier = modifier,
        textAlign = TextAlign.Center,
        color = color, // Customize color as needed
        fontWeight = FontWeight.Bold,
        fontSize = fontSize
    )
}

@Composable
fun TextLabel(
    modifier: Modifier = Modifier,
    text: String = "Empty"
) {
    val defaultModifier = Modifier
        .fillMaxWidth()
        .padding(start = 8.dp)

    val appliedModifier = if (modifier == Modifier) {
        defaultModifier // Use default if no custom modifier is provided
    } else {
        modifier // Use the provided modifier otherwise
    }

    Text(
        text = text,
        modifier = appliedModifier,
        textAlign = TextAlign.Start,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    )
}