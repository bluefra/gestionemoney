package gestionemoney.compose.expense.components

import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DatePicker(
    initialDate: Date,
    onDateChanged: (Date) -> Unit
) {
    var date by remember { mutableStateOf(initialDate) }
    var text by remember { mutableStateOf(TextFieldValue()) }

    val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    Column {
        Spacer(modifier = Modifier.height(16.dp))
        Text("Select Date: ${dateFormat.format(date)}")
        Text("Insert Date (dd/MM/yyyy):")
        TextField(
            value = text,
            onValueChange = {
                text = it
                val parsedDate = parseDate(text.text, initialDate)
                if (parsedDate != null) {
                    date = parsedDate
                    onDateChanged(date)
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

fun parseDate(text: String, minDate: Date): Date? {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val date: Date?
    try {
        date = dateFormat.parse(text)
        if(date == null) {
            return null
        }
        if(date.before(minDate)) {
            return null
        }
        return date
    } catch (e: Exception) {
        return null
    }
}
