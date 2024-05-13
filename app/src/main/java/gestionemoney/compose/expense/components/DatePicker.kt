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
        return setTime(date)
    } catch (e: Exception) {
        return null
    }
}

/**
 * aggiunge ora, minuti e secondi attuali alla data passata
 */
fun setTime(date: Date): Date {
    // Creazione dell'oggetto Calendar
    val calendarNow = Calendar.getInstance()
    val calendarDate = Calendar.getInstance()
    // Impostazione dell'ora attuale sull'oggetto Calendar
    calendarNow.time = Date()
    calendarDate.time = date
    // Ottenimento dell'ora attuale
    val currentHour = calendarNow.get(Calendar.HOUR_OF_DAY)
    val currentMinute = calendarNow.get(Calendar.MINUTE)
    val currentSecond = calendarNow.get(Calendar.SECOND)

    // Impostazione dell'ora corrente sull'oggetto Date
    calendarDate.set(Calendar.HOUR_OF_DAY, currentHour)
    calendarDate.set(Calendar.MINUTE, currentMinute)
    calendarDate.set(Calendar.SECOND, currentSecond)

    // Aggiornamento dell'oggetto Date
    return calendarDate.time
}

