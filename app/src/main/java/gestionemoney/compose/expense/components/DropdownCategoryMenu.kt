package gestionemoney.compose.expense.components

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownCategoryMenu() {

    val context = LocalContext.current
    val categories = arrayOf("Abbigliamento", "Alimentari", "Ristoranti", "Uscite varie")
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(categories[0]) }

    Row(
    ) {
        // Composable native function.
        ExposedDropdownMenuBox(
            expanded = expanded ,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedCategory ,
                onValueChange = {} ,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) } ,
                modifier = Modifier.menuAnchor()
            )
            //Composable native function.
            ExposedDropdownMenu(
                expanded = expanded ,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(text = "category") } ,
                        onClick = {
                            selectedCategory = category
                            expanded = false
                            Toast.makeText(context , category , Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}