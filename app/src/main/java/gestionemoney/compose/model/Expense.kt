package gestionemoney.compose.model

import java.util.Date

class Expense(private val date: Date = Date(), private val value: Double, private var name: String = ""){
    /**
     * @return
     * if date == comparedDate -> return 0
     * if date > comparedDate -> return 1
     * if date < comparedDate -> return -1
     */
    fun compareDate(comparedDate: Date): Int {
        return date.compareTo(comparedDate)
    }

    /**
     *@return
     * if date == comparedDate -> return 0
     * if date > comparedDate -> return 1
     * if date < comparedDate -> return -1
     */
    fun compareDateByString(stringDate: String): Boolean {
        return DateAdapter().getStringDate(date) == stringDate
    }

    /**
     * @return 0 -> e1.value == this.value, 1 -> e1.value < this.value, -1 -> e1.value > this.value
     */
    fun confrontValue(e1: Expense): Int{
        if(value == e1.value) { return 0 }
        if(value > e1.value) { return 1 }
        return -1
    }

    /**
     * @return value
     */
    fun getValue(): Double {
        return value
    }
    /**
     * @return date
     */
    fun getDate(): Date {
        return date
    }
    /**
     * modify the name associated with the expense
     */
    fun setName(newName: String) {
        name = newName
    }

    /**
     * @return name
     */
    fun getName(): String {
        return name
    }
    override fun toString(): String {
        return " {$date -> $value} "
    }

    /**
     * it will build the name as the expense should be stored in the db (DBname).
     * return DBname
     */
    fun getDBName(): String {
        return DateAdapter().getStringDate(date) + DBtoken + name
    }

    /**
     * used to generate the DB name, (expense.date(as per iso) DB_token expense.name)
     */
    companion object {
        /**
         * token used to separate the data into the db
         */
        const val DBtoken: String = "_"

        /**
         * @return expense build with the data contained into the DB name, using the value passed as
         * parameter
         */
        fun loadExpenseFromDB(name: String, value: Double): Expense {
            val results = name.split(DBtoken)
            return Expense(DateAdapter().buildDate(results[0]), value, results[1])
        }
    }
}