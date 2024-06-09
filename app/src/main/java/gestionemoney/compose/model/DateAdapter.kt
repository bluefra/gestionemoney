package gestionemoney.compose.model

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

/**
 * class used to resolve the different time zone problems
 */
class DateAdapter {
    /**
     * date format using the iso standard
     */
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS XXX", Locale.getDefault())
    /**
     * rebuild the date passed as a string following the iso standard
     */
    fun buildDate(stringDate: String): Date {
        return dateFormat.parse(stringDate)!!
    }

    /**
     * rebuild the date passed as a parameter to a string following the iso standard
     */
    fun getStringDate(date: Date): String {
        return dateFormat.format(date)
    }
}

/**
 * @return the day of the week as a number, 1 -> monday, 7 -> sunday
 */
fun weekDay(): Int {
    val calendar = Calendar.getInstance()
    // 'u' tu get the day of the week as a number(1 - 7)
    val dateFormat = SimpleDateFormat("u", Locale.getDefault())
    return dateFormat.format(calendar.time).toInt()
}

/**
 * @return return the difference between the date passed as parameter and now(),
 * -> the format will be: %d giorni, %d ore, %d minuti, %d secondi
 */
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

/**
 * return the difference in days from the date passed and now()
 */
fun getDateDifferenceFromNowDay(date: Date): Double {
    val diffInMillies = Date().time - date.time
    val diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillies)
    return diffInDays.toDouble()
}

/**
 * @return the date passed as parameter as a String using the common standard of the computer
 */
fun formatReadingDate(date: Date): String {
    return SimpleDateFormat("EEEE dd/MM/yyyy", Locale.getDefault()).format(date)
}