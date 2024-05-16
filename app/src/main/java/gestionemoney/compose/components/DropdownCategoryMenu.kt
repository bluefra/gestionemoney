package gestionemoney.compose.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownCategoryMenu(categoryList: List<String>, standardOption: String, onChange: (String) -> Unit = {}) {
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(standardOption) }

    Row{
        // Composable native function.
        ExposedDropdownMenuBox(
            expanded = expanded ,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedCategory ,
                onValueChange = {
                    selectedCategory = it
                    onChange(it)
                },
                enabled = false,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) } ,
                modifier = Modifier.menuAnchor()
            )
            //Composable native function.
            ExposedDropdownMenu(
                expanded = expanded ,
                onDismissRequest = { expanded = false }
            ) {
                categoryList.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(text = category) } ,
                        onClick = {
                            selectedCategory = category
                            expanded = false
                            onChange(category)
                        }
                    )
                }
            }
        }
    }
}