package gestionemoney.compose.model

import java.util.Date

class Expense(private val date: Date = Date(), private val value: Double, private var name: String = ""){
    /**
     * if date == comparedDate -> return 0
     * if date > comparedDate -> return 1
     * if date < comparedDate -> return -1
     */
    fun compareDate(comparedDate: Date): Int {
        return date.compareTo(comparedDate)
    }

    fun compareDateByString(stringDate: String): Boolean {
        return DateAdapter().getStringDate(date) == stringDate
    }
    fun confrontValue(e1: Expense): Int{
        if(value == e1.value) { return 0 }
        if(value > e1.value) { return 1 }
        return -1
    }

    fun getValue(): Double {
        return value
    }

    fun getDate(): Date {
        return date;
    }
    fun setName(newName: String) {
        name = newName
    }
    fun getName(): String {
        return name
    }
    override fun toString(): String {
        return " {$date -> $value} "
    }

    fun getDBName(): String {
        return DateAdapter().getStringDate(date) + DBtoken + name;
    }
    companion object {
        const val DBtoken: String = "_"

        fun loadExpenseFromDB(name: String, value: Double): Expense {
            val results = name.split(DBtoken)
            return Expense(DateAdapter().buildDate(results[0]), value, results[1])
        }
    }
}