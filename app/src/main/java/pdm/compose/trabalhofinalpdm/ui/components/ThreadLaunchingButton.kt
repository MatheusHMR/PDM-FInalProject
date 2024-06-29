package pdm.compose.trabalhofinalpdm.ui.components


import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@Composable
fun ThreadLaunchingButton (
    onClick: suspend() -> Unit,
    text: String,
    modifier: Modifier = Modifier
){
    var isLoading by remember { mutableStateOf(false) }
    var coroutineScope = rememberCoroutineScope()

    Button(
        onClick = {
            coroutineScope.launch {
                isLoading = true
                onClick()
                isLoading = false
            }
        },
        modifier = Modifier,
    ) {
        if(isLoading) {
            CircularProgressIndicator()
        } else {
            Text( text = text)
        }
    }
}
