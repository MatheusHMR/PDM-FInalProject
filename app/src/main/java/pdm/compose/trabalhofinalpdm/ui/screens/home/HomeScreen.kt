package pdm.compose.trabalhofinalpdm.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.text.style.TextAlign

@Composable
fun HomeScreen(
    navController: NavHostController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imageUrl = "https://images.unsplash.com/photo-1511920170033-f8396924c348?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDJ8fGNvZmZlZXxlbnwwfHx8fDE2ODQ4ODMwNjU&ixlib=rb-1.2.1&q=80&w=400"
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = "Coffee Image",
            modifier = Modifier
                .size(450.dp)
                .padding(16.dp)
        )

        Text(
            text = "Welcome Aboard,\nDear Coffee Lover!",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Cursive, // Experiment with different font families
            color = Color.DarkGray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp)
        )
    }
}
