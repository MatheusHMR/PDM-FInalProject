package pdm.compose.trabalhofinalpdm.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GenericActionMenu(
    actionTargetString: String,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onEditClick: (() -> Unit)? = null, // Nullable callbacks
    onDeleteClick: (() -> Unit)? = null,
    onMoreInfoClick: (() -> Unit)? = null
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // More Info Button
            onMoreInfoClick?.let {
                DropdownMenuItem(
                    text = { Text("More of $actionTargetString") },
                    onClick = it,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "More of $actionTargetString"
                        )
                    }
                )
            }

            // Edit Button
            onEditClick?.let {
                DropdownMenuItem(
                    text = { Text("Edit $actionTargetString") },
                    onClick = it,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit$actionTargetString"
                        )
                    }
                )
            }

            // Delete Button
            onDeleteClick?.let {
                DropdownMenuItem(
                    text = { Text("Delete $actionTargetString") },
                    onClick = it,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete $actionTargetString"
                        )
                    }
                )
            }
        }
    }
}