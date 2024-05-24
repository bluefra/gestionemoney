package gestionemoney.compose.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateAdapter {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS z", Locale.getDefault())

    fun buildDate(stringDate: String): Date {
        return dateFormat.parse(stringDate)!!
    }

    fun getStringDate(date: Date): String {
        return dateFormat.format(date)
    }
}