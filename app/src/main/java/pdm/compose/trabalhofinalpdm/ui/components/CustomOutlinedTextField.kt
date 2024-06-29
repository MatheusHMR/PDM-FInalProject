package pdm.compose.trabalhofinalpdm.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun CustomOutlinedTextField (
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    validation: (String) -> String? = {null}
//    Validation function passed as a parameter coming from the MainViewModel
){
    var validationError by remember {
        mutableStateOf<String?>(null) }

    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            onValueChange(newValue)
            validationError = validation(newValue)
            //Call the validation function
        },
        label = { Text( text = label) },
        modifier = modifier,
        enabled = enabled,
        isError = validationError != null,
        keyboardOptions = KeyboardOptions(keyboardType =
            keyboardType),
        supportingText = {
            validationError?.let {message -> Text(message) }
        }
    )
}