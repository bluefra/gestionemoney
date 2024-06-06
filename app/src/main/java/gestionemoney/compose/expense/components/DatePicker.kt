package gestionemoney.compose.expense.components

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import gestionemoney.compose.R
import gestionemoney.compose.components.NormalText
import gestionemoney.compose.components.TextFiledType
import gestionemoney.compose.model.DateAdapter
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DatePicker(
    defaultDate: Date,
    initialDate: Date,
    onDateChanged: (Date) -> Unit
) {
    var date by rememberSaveable { mutableStateOf(defaultDate) }
    var text by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    val keyboardController = LocalSoftwareKeyboardController.current

    val dateFormat = rememberSaveable { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    Column {
        Spacer(modifier = Modifier.height(15.dp))
        NormalText(string = stringResource(id = R.string.insert_data))
        Spacer(modifier = Modifier.height(5.dp))
        TextField(
            modifier = Modifier.border(2.dp, color = colorResource(id = R.color.orange), shape = RoundedCornerShape(50)),
            value = text,
            onValueChange = {
                text = it
                val parsed = parseDate(text.text, initialDate)
                if (parsed != null) {
                    date = parsed
                    onDateChanged(date)
                }
            },
            label = {
                TextFiledType(string = stringResource(id = R.string.preselected_data) + " ${dateFormat.format(date)}")
               },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {keyboardController?.hide()}
            ),
            colors =  TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.height(5.dp))
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
    Log.w("date conversion", DateAdapter().getStringDate(date))
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
    val currentMilli = calendarNow.get(Calendar.MILLISECOND)
    // Impostazione dell'ora corrente sull'oggetto Date
    calendarDate.set(Calendar.HOUR_OF_DAY, currentHour)
    calendarDate.set(Calendar.MINUTE, currentMinute)
    calendarDate.set(Calendar.SECOND, currentSecond)
    calendarDate.set(Calendar.MILLISECOND, currentMilli)
    // Aggiornamento dell'oggetto Date
    return calendarDate.time
}

