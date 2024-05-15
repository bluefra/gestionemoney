package gestionemoney.compose.model

import java.util.Date

class Expense(private val date: Date = Date(), private val value: Double){
    private var name: String = ""
    /**
     * if date == comparedDate -> return 0
     * if date > comparedDate -> return 1
     * if date < comparedDate -> return -1
     */
    fun confrontDate(e1: Expense): Int {
        val result: Int = date.compareTo(e1.date)
        if(result == 0) { return 0 }
        if(result > 0) { return 1 }
        return -1
    }

    fun confrontValue(e1: Expense): Int{
        if(value == e1.value) { return 0 }
        if(value > e1.value) { return 1 }
        return -1
    }

    fun getValue(): Double {
        return value
    }

    fun getStringDate(): String {
        return  date.toString()
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
        return date.toString() + DBtoken + name;
    }
    companion object {
        const val DBtoken: String = "_"

        fun loadExpenseFromDB(name: String, value: Double): Expense {
            val results = name.split(DBtoken)
            val expense = Expense(Date(results[0]), value)
                expense.setName(results[1])
            return expense
        }
    }
}