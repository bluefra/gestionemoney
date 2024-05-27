package gestionemoney.compose.model

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class DateAdapter {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS XXX", Locale.getDefault())

    fun buildDate(stringDate: String): Date {
        return dateFormat.parse(stringDate)!!
    }

    fun getStringDate(date: Date): String {
        return dateFormat.format(date)
    }
}

fun weekDay(): Int {
    val calendar = Calendar.getInstance()
    // 'u' per ottenere giorno della settimana numerico (1-7)
    val dateFormat = SimpleDateFormat("u", Locale.getDefault())
    return dateFormat.format(calendar.time).toInt()
}

fun getDateDifferenceFromNow(date: Date): String {
    val diffInMillies = Date().time - date.time
    val diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(diffInMillies)
    val diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillies)
    val diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillies)
    val diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillies)
    return String.format(
        Locale.getDefault(),
        "%d giorni, %d ore, %d minuti, %d secondi",
        diffInDays,
        diffInHours % 24,
        diffInMinutes % 60,
        diffInSeconds % 60
    )
}

fun getDateDifferenceFromNowDay(date: Date): Double {
    val diffInMillies = Date().time - date.time
    val diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillies)
    return diffInDays.toDouble()
}
fun getDateDifferenceFromNow(date: String): String {
    if(date == "") {
        return date
    }
    return getDateDifferenceFromNow(Date(date))
}

fun formatReadingDate(date: Date): String {
    return SimpleDateFormat("EEEE dd/MM/yyyy", Locale.getDefault()).format(date)
}