package gestionemoney.compose.model

import android.util.Log
import java.util.Date


class Category (private val name: String, private var imageURI: String = ""){
    private var expensesList: MutableList<Expense> = mutableListOf()
    fun addExpenses(expense: Expense) {
        Log.w("Category", "adding $expense")
        expensesList.add(expense)
    }
    fun compareName(c1: Category): Int {
        return name.compareTo(c1.name)
    }
    fun compareName(comparedName: String): Int {
        return name.compareTo(comparedName)
    }

    fun orderByValue(order: ORDER): List<Expense> {
        return when (order) {
            ORDER.ASC -> expensesList.sortedWith { e0, e1 -> e0.confrontValue(e1) }
            ORDER.DEC -> expensesList.sortedWith { e0, e1 -> -e0.confrontValue(e1) }
        }
    }

    fun orderByDate(order: ORDER): List<Expense> {
        return when (order) {
            ORDER.ASC -> expensesList.sortedWith { e0, e1 -> e0.confrontExpense(e1) }
            ORDER.DEC -> expensesList.sortedWith { e0, e1 -> -e0.confrontExpense(e1) }
        }
    }

    fun getList(): List<Expense> {
        return expensesList
    }

    fun getName(): String {
        return name
    }

    fun setImage(uri: String) {
        imageURI = uri
    }
    fun getImageURI(): String? {
        return imageURI
    }

    fun getDBname(): String {
        var nameDB = name
        if(imageURI != null) {
            nameDB += DBtoken + imageURI
        }
        Log.w("dbextra", "nameDBCategory: "+nameDB)
        return nameDB
    }

    fun GetTotalExpences(): Double {
        var tot: Double = 0.0
        expensesList.forEach {
            tot += it.getValue()
        }
        return tot
    }

    fun getExpense(dateString: String): Expense? {
        val date = Date(dateString)
        expensesList.forEach{
            if(it.compareDate(date) == 0) { return it }
        }
        return null
    }
    fun lastExpenseHashMap(): HashMap<String, Any>? {
        if(expensesList.size == 0) { return null }
        val map: HashMap<String, Any> = HashMap()
        val lastExpense = expensesList.last()
        map[lastExpense.getDBName()] = lastExpense.getValue()
        return map
    }
    fun toHashmap(): HashMap<String, Any> {
        val map: HashMap<String, Any> = HashMap()
        expensesList.forEach() {
            map[it.getDBName()] = it.getValue()
        }
        return map
    }

    fun loadFromHashmap(map: HashMap<String, Double>) {
        map.forEach {
            expensesList.add(Expense.loadExpenseFromDB(it.key, it.value))
        }
    }
    override fun toString(): String {
        var s = "$name:\t"
        if(imageURI != null) {
            s += "imageURI: $imageURI\n"
        }
        expensesList.forEach() {
            s+= it.toString()
        }
        return s
    }
    enum class ORDER{
        ASC,
        DEC
    }

    companion object {
        const val DBtoken: String = "_"

        fun loadCategoryFromDB(name: String): Category {
            val results = name.split(DBtoken)
            return Category(results[0], results[1])
        }
    }
}